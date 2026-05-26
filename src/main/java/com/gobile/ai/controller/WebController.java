package com.gobile.ai.controller;

import com.gobile.ai.model.CaseForm;
import com.gobile.ai.service.AiService;
import com.gobile.ai.service.PdfService;
import com.gobile.ai.service.PromptBuilder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@Controller
public class WebController {

    private final AiService aiService;

    private final PdfService pdfService;

    // =========================================
    // CONSTRUCTOR
    // =========================================

    public WebController(
            AiService aiService,
            PdfService pdfService
    ) {
        this.aiService = aiService;
        this.pdfService = pdfService;
    }

    // =========================================
    // HOME PAGE
    // =========================================

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "caseForm",
                new CaseForm()
        );

        return "index";
    }

    // =========================================
    // GENERATE LEGAL DOCUMENT
    // =========================================

    @PostMapping("/generate")
    public String generateDocument(
            @ModelAttribute CaseForm caseForm,
            Model model
    ) {

        String prompt =
                PromptBuilder.buildLegalPrompt(caseForm);

        String generatedDocument =
                aiService.generateLegalDocument(prompt);

        model.addAttribute(
                "document",
                generatedDocument
        );

        model.addAttribute(
                "caseForm",
                caseForm
        );

        return "result";
    }
    // =========================================
    // DOWNLOAD PDF
    // =========================================

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf(
            @RequestParam String title,
            @RequestParam String content
    ) {

        ByteArrayInputStream pdf =
                pdfService.generatePdf(title, content);

        byte[] pdfBytes;

        try {

            pdfBytes = pdf.readAllBytes();

        } catch (Exception e) {

            pdfBytes = new byte[0];
        }

        HttpHeaders headers = new HttpHeaders();

        headers.add(
                "Content-Disposition",
                "attachment; filename=legal-document.pdf"
        );

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}