package com.gobile.ai.service;

import com.gobile.ai.model.CaseForm;

public class PromptBuilder {

    public static String buildLegalPrompt(CaseForm form) {

        String basePrompt = """
                You are an AI legal drafting assistant for Gobile & Associates Incorporated.

                Apply South African law.
                Use professional legal drafting style.
                Use numbered paragraphs where appropriate.
                Include disclaimer for attorney review.
                """;

        switch (form.getCaseType()) {
        
        	case "CIVIL_LITIGATION":
        		return basePrompt + buildCivilLitigationPrompt(form);
            
            case "CRIMINAL":
                return basePrompt + buildCriminalPrompt(form);

            case "COMMERCIAL":
                return basePrompt + buildCommercialPrompt(form);

            case "ADVISORY":
                return basePrompt + buildAdvisoryPrompt(form);

            default:
                return basePrompt + buildGeneralPrompt(form);
        }
    }
    
    private static String buildCivilLitigationPrompt(CaseForm form) {

        return """
                Draft a South African civil litigation document.

                Document Type:
                %s

                Client Name:
                %s

                Opposing Party:
                %s

                Jurisdiction:
                %s

                Facts:
                %s

                Relief Sought:
                %s

                Additional Notes:
                %s

                Requirements:
                - Apply South African civil procedure
                - Use formal court drafting style
                - Structure document professionally
                - Include legal terminology
                """
                .formatted(
                        form.getDocumentType(),
                        form.getClientName(),
                        form.getOpposingParty(),
                        form.getJurisdiction(),
                        form.getFacts(),
                        form.getReliefSought(),
                        form.getAdditionalNotes()
                );
    }

    private static String buildCriminalPrompt(CaseForm form) {

        return """
                Draft a South African criminal law document.

                Document Type:
                %s

                Client Name:
                %s

                Jurisdiction:
                %s

                Facts:
                %s

                Relief Sought:
                %s

                Additional Notes:
                %s

                Requirements:
                - Apply South African criminal procedure
                - Use formal legal drafting
                - Use professional court language
                - Include constitutional considerations where relevant
                """
                .formatted(
                        form.getDocumentType(),
                        form.getClientName(),
                        form.getJurisdiction(),
                        form.getFacts(),
                        form.getReliefSought(),
                        form.getAdditionalNotes()
                );
    }

    private static String buildCommercialPrompt(CaseForm form) {

        return """
                Draft a South African commercial law document.

                Document Type:
                %s

                Client Name:
                %s

                Opposing Party:
                %s

                Facts:
                %s

                Additional Notes:
                %s

                Requirements:
                - Use professional commercial drafting
                - Include contractual protections
                - Apply South African commercial law
                - Use formal legal terminology
                """
                .formatted(
                        form.getDocumentType(),
                        form.getClientName(),
                        form.getOpposingParty(),
                        form.getFacts(),
                        form.getAdditionalNotes()
                );
    }
    
    private static String buildAdvisoryPrompt(CaseForm form) {

        return """
                Provide a South African legal advisory opinion.

                Client Name:
                %s

                Facts:
                %s

                Additional Notes:
                %s

                Requirements:
                - Explain legal position clearly
                - Identify risks
                - Suggest legal remedies
                - Use professional advisory tone
                - Apply South African law
                """
                .formatted(
                        form.getClientName(),
                        form.getFacts(),
                        form.getAdditionalNotes()
                );
    }
    
    private static String buildGeneralPrompt(CaseForm form) {
        return form.getFacts();
    }
}