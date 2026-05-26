package com.gobile.ai.service;

import org.springframework.stereotype.Service;

@Service
public class DemoResponseService {
	
	

    public String getDemoLegalDocument(String prompt) {

        String lowerPrompt = prompt.toLowerCase();

        // =====================================================
        // CRIMINAL LAW
        // =====================================================

        if (lowerPrompt.contains("bail")) {
            return bailApplicationDemo(prompt);
        }

        if (lowerPrompt.contains("mitigation")) {
            return mitigationDemo(prompt);
        }

        if (lowerPrompt.contains("plea")) {
            return pleaDemo(prompt);
        }
        
        if (lowerPrompt.contains("particulars of claim")) {
            return particularsOfClaimDemo();
        }


        // =====================================================
        // COMMERCIAL LAW
        // =====================================================

        if (lowerPrompt.contains("nda")
                || lowerPrompt.contains("non-disclosure")) {

            return ndaDemo(prompt);
        }

        if (lowerPrompt.contains("letter of demand")) {
            return demandLetterDemo(prompt);
        }

        if (lowerPrompt.contains("service agreement")) {
            return serviceAgreementDemo(prompt);
        }

        // =====================================================
        // ADVISORY
        // =====================================================

        if (lowerPrompt.contains("legal opinion")
                || lowerPrompt.contains("advisory")) {

            return legalOpinionDemo(prompt);
        }

        // =====================================================
        // URGENT APPLICATION
        // =====================================================

        if (lowerPrompt.contains("urgent")) {
            return urgentApplicationDemo(prompt);
        }

        // =====================================================
        // DEFAULT
        // =====================================================

        return generalDemo(prompt);
    }
    
    private String particularsOfClaimDemo() {

        return """
                IN THE MAGISTRATE'S COURT FOR THE DISTRICT OF JOHANNESBURG

                CASE NO: ___________

                In the matter between:

                JOHN DOE                                    Plaintiff

                and

                ABC TECHNOLOGIES                           Defendant

                ________________________________________

                        PARTICULARS OF CLAIM
                ________________________________________

                1. The Plaintiff is an adult male residing
                   within the jurisdiction of this Honourable Court.

                2. The Defendant is a registered company
                   conducting business within the Republic
                   of South Africa.

                3. During January 2026 the parties entered
                   into a written service agreement.

                4. The Plaintiff duly rendered services in
                   terms of the agreement.

                5. The Defendant failed to make payment
                   in the amount of R45,000.

                6. Despite demand, payment remains outstanding.

                WHEREFORE the Plaintiff claims:

                a) Payment of R45,000;
                b) Interest thereon;
                c) Costs of suit.

                __________________________
                PLAINTIFF'S ATTORNEYS

                DISCLAIMER:
                Subject to attorney review.
                """;
    }

    // =========================================================
    // BAIL APPLICATION
    // =========================================================

    private String bailApplicationDemo(String prompt) {

        return """
                IN THE MAGISTRATE'S COURT OF SOUTH AFRICA

                BAIL APPLICATION

                The Accused respectfully applies for bail pending trial.

                GROUNDS FOR BAIL

                1. The accused has a fixed residential address.

                2. The accused is gainfully employed.

                3. The accused is not a flight risk.

                4. The accused undertakes to stand trial.

                5. The accused has no previous convictions.

                FACTUAL BACKGROUND

                %s

                RELIEF SOUGHT

                The accused requests release on reasonable bail conditions.

                DISCLAIMER:
                This document is generated for internal drafting
                purposes and remains subject to attorney review.
                """
                .formatted(prompt);
    }

    // =========================================================
    // MITIGATION
    // =========================================================

    private String mitigationDemo(String prompt) {

        return """
                SENTENCING MITIGATION SUBMISSION

                The accused respectfully submits the following
                mitigating factors:

                1. The accused is a first offender.

                2. The accused has shown remorse.

                3. The accused is the sole provider for dependants.

                4. Rehabilitation remains possible.

                FACTUAL DETAILS

                %s

                DISCLAIMER:
                Subject to attorney review.
                """
                .formatted(prompt);
    }

    // =========================================================
    // PLEA
    // =========================================================

    private String pleaDemo(String prompt) {

        return """
                IN THE MAGISTRATE'S COURT

                PLEA

                The Defendant denies each and every allegation
                contained in the particulars of claim.

                LEGAL BASIS

                1. No enforceable agreement existed.

                2. The Plaintiff is put to the proof thereof.

                FACTUAL SUMMARY

                %s

                DISCLAIMER:
                Subject to attorney approval.
                """
                .formatted(prompt);
    }

    // =========================================================
    // NDA
    // =========================================================

    private String ndaDemo(String prompt) {

        return """
                NON-DISCLOSURE AGREEMENT

                The parties agree to maintain confidentiality
                regarding proprietary and confidential information.

                OBLIGATIONS

                1. No disclosure to third parties.

                2. Information may only be used for authorized purposes.

                3. Breach may result in legal action.

                BACKGROUND

                %s

                DISCLAIMER:
                This agreement draft requires legal review.
                """
                .formatted(prompt);
    }

    // =========================================================
    // DEMAND LETTER
    // =========================================================

    private String demandLetterDemo(String prompt) {

        return """
                LETTER OF DEMAND

                TAKE NOTICE that payment is demanded
                within 7 days from date of receipt hereof.

                Failure to comply may result in legal proceedings
                without further notice.

                FACTUAL BASIS

                %s

                DISCLAIMER:
                Subject to attorney review.
                """
                .formatted(prompt);
    }

    // =========================================================
    // SERVICE AGREEMENT
    // =========================================================

    private String serviceAgreementDemo(String prompt) {

        return """
                SERVICE AGREEMENT

                This agreement governs the rendering of professional
                services between the parties.

                TERMS

                1. Scope of services.

                2. Payment obligations.

                3. Confidentiality obligations.

                4. Termination provisions.

                BACKGROUND

                %s

                DISCLAIMER:
                This agreement requires legal review.
                """
                .formatted(prompt);
    }

    // =========================================================
    // LEGAL OPINION
    // =========================================================

    private String legalOpinionDemo(String prompt) {

        return """
                LEGAL OPINION

                Based on the supplied facts and South African law,
                the following legal position is noted:

                1. Potential procedural unfairness exists.

                2. Further legal remedies may be available.

                3. The matter should be assessed in detail.

                FACTUAL BASIS

                %s

                DISCLAIMER:
                This opinion is preliminary and subject
                to attorney review.
                """
                .formatted(prompt);
    }

    // =========================================================
    // URGENT APPLICATION
    // =========================================================

    private String urgentApplicationDemo(String prompt) {

        return """
                URGENT COURT APPLICATION

                The Applicant approaches the Court on an urgent basis.

                GROUNDS OF URGENCY

                1. Irreparable harm may occur.

                2. No alternative remedy exists.

                3. Immediate intervention is necessary.

                FACTUAL BASIS

                %s

                DISCLAIMER:
                Subject to legal practitioner review.
                """
                .formatted(prompt);
    }

    // =========================================================
    // GENERAL DEMO
    // =========================================================

    private String generalDemo(String prompt) {

        return """
                LEGAL MEMORANDUM

                This memorandum is generated based on
                supplied case facts and South African law.

                CASE INFORMATION

                %s

                DISCLAIMER:
                This document is for internal drafting purposes
                only and must be reviewed by a qualified attorney.
                """
                .formatted(prompt);
    }
}