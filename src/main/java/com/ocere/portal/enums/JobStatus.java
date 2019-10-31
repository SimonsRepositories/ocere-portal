package com.ocere.portal.enums;

// Prototype
public enum JobStatus {
    Live("Live");

    private String description;

    JobStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
