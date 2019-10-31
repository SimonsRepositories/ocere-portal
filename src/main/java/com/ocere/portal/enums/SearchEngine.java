package com.ocere.portal.enums;

public enum SearchEngine {
    CA(".ca"),
    COUK(".co.uk"),
    COM(".com"),
    COZA(".co.za"),
    COMAU(".com.au"),
    DE(".de"),
    DK(".dk"),
    FR(".fr"),
    IE(".ie"),
    PT(".pt");

    private String description;

    SearchEngine(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
