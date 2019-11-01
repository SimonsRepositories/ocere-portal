package com.ocere.portal.enums;

// Prototype
public enum JobStatus {
    ACTIVE("Active"), COMPLETED("Completed");

    private String description;

    JobStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
