package com.ocere.portal.enums;

public enum DynamicTurnaround {
    NONE("None"), JOBEND("End of Job"), CAMPAIGNSTART("Campaign Start");

    private String description;

    DynamicTurnaround(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
