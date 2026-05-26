package com.gobile.ai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AiService {

    private static final Logger log =
            LoggerFactory.getLogger(AiService.class);

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${ai.demo.mode}")
    private boolean demoMode;

    private final DemoResponseService demoResponseService;

    private static final String OPENAI_URL =
            "https://api.openai.com/v1/chat/completions";

    public AiService(DemoResponseService demoResponseService) {
        this.demoResponseService = demoResponseService;
    }

    // =========================================================
    // GENERATE LEGAL DOCUMENT
    // =========================================================

    public String generateLegalDocument(String prompt) {

        log.info("Starting legal document generation...");

        // =====================================================
        // DEMO MODE
        // =====================================================

        if (demoMode) {

            log.info("Running in DEMO MODE");

            String demoResponse =
                    demoResponseService.getDemoLegalDocument(prompt);

            return cleanAiResponse(demoResponse);
        }

        // =====================================================
        // REAL OPENAI MODE
        // =====================================================

        try {

            RestTemplate restTemplate =
                    new RestTemplate();

            // -------------------------------------------------
            // SYSTEM MESSAGE
            // -------------------------------------------------

            Map<String, String> systemMessage =
                    new HashMap<>();

            systemMessage.put("role", "system");

            systemMessage.put(
                    "content",
                    """
                    You are an AI legal drafting assistant
                    for Gobile & Associates Incorporated.

                    Apply South African law where relevant.

                    Use:
                    - professional legal drafting
                    - formal legal tone
                    - structured legal reasoning
                    - numbered paragraphs where appropriate

                    IMPORTANT:
                    Return ONLY the final legal document.

                    Do NOT include:
                    - factual background headings
                    - prompt instructions
                    - drafting explanations
                    - notes to the user
                    - AI commentary
                    - metadata

                    The response must look like a
                    professionally drafted legal document.

                    Always include a disclaimer stating
                    that attorney review is required.
                    """
            );

            // -------------------------------------------------
            // USER MESSAGE
            // -------------------------------------------------

            Map<String, String> userMessage =
                    new HashMap<>();

            userMessage.put("role", "user");

            userMessage.put("content", prompt);

            // -------------------------------------------------
            // MESSAGE LIST
            // -------------------------------------------------

            List<Map<String, String>> messages =
                    new ArrayList<>();

            messages.add(systemMessage);

            messages.add(userMessage);

            // -------------------------------------------------
            // REQUEST BODY
            // -------------------------------------------------

            Map<String, Object> requestBody =
                    new HashMap<>();

            requestBody.put("model", "gpt-4o-mini");

            requestBody.put("messages", messages);

            requestBody.put("temperature", 0.2);

            requestBody.put("max_tokens", 2000);

            // -------------------------------------------------
            // HEADERS
            // -------------------------------------------------

            HttpHeaders headers =
                    new HttpHeaders();

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );

            headers.setBearerAuth(apiKey);

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(
                            requestBody,
                            headers
                    );

            log.info("Sending request to OpenAI...");

            // -------------------------------------------------
            // API CALL
            // -------------------------------------------------

            Map<String, Object> response =
                    restTemplate.postForObject(
                            OPENAI_URL,
                            entity,
                            Map.class
                    );

            // -------------------------------------------------
            // VALIDATE RESPONSE
            // -------------------------------------------------

            if (response == null) {

                log.error("OpenAI response is null");

                return """
                        ERROR:
                        No response received from AI service.
                        """;
            }

            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>)
                            response.get("choices");

            if (choices == null || choices.isEmpty()) {

                log.error("No choices returned from OpenAI");

                return """
                        ERROR:
                        AI service returned an empty response.
                        """;
            }

            Map<String, Object> firstChoice =
                    choices.get(0);

            Map<String, String> message =
                    (Map<String, String>)
                            firstChoice.get("message");

            if (message == null ||
                    !message.containsKey("content")) {

                log.error(
                        "AI response message missing content"
                );

                return """
                        ERROR:
                        Invalid AI response format.
                        """;
            }

            String generatedDocument =
                    message.get("content");

            // =================================================
            // CLEAN RESPONSE
            // =================================================

            String cleanedDocument =
                    cleanAiResponse(generatedDocument);

            log.info(
                    "Legal document generated successfully"
            );

            return cleanedDocument;

        } catch (RestClientException ex) {

            log.error(
                    "OpenAI API communication error",
                    ex
            );

            return """
                    ERROR:
                    Unable to communicate with AI service.

                    Please try again later.
                    """;

        } catch (Exception ex) {

            log.error(
                    "Unexpected AI generation error",
                    ex
            );

            return """
                    ERROR:
                    An unexpected error occurred while
                    generating the legal document.
                    """;
        }
    }

    // =========================================================
    // CLEAN AI RESPONSE
    // =========================================================

    private String cleanAiResponse(String response) {

        if (response == null) {
            return "";
        }

        // Remove factual background sections

        response = response.replaceAll(
                "(?is)FACTUAL BACKGROUND.*?(?=RELIEF SOUGHT|DISCLAIMER|$)",
                ""
        );

        // Remove prompt instructions

        response = response.replaceAll(
                "(?is)Document Type:.*?(?=RELIEF SOUGHT|DISCLAIMER|$)",
                ""
        );

        response = response.replaceAll(
                "(?is)Requirements:.*?(?=RELIEF SOUGHT|DISCLAIMER|$)",
                ""
        );

        response = response.replaceAll(
                "(?is)Additional Notes:.*?(?=RELIEF SOUGHT|DISCLAIMER|$)",
                ""
        );

        response = response.replaceAll(
                "(?is)Client Name:.*?(?=RELIEF SOUGHT|DISCLAIMER|$)",
                ""
        );

        response = response.replaceAll(
                "(?is)Jurisdiction:.*?(?=RELIEF SOUGHT|DISCLAIMER|$)",
                ""
        );

        response = response.replaceAll(
                "(?is)Facts:.*?(?=RELIEF SOUGHT|DISCLAIMER|$)",
                ""
        );

        // Remove excessive blank lines

        response = response.replaceAll(
                "\\n{3,}",
                "\n\n"
        );

        return response.trim();
    }
}