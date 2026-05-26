package com.gobile.ai.model;

public class CaseForm {

    private String jurisdiction;
    private String caseType;
    private String documentType;
    private String clientName;
    private String opposingParty;
    private String reliefSought;
    private String facts;
    private String additionalNotes;

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOpposingParty() {
        return opposingParty;
    }

    public void setOpposingParty(String opposingParty) {
        this.opposingParty = opposingParty;
    }

    public String getReliefSought() {
        return reliefSought;
    }

    public void setReliefSought(String reliefSought) {
        this.reliefSought = reliefSought;
    }

    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }
}