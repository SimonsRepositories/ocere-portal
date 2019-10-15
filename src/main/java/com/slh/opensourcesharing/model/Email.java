package com.slh.opensourcesharing.model;

public class Email
{
    private String setFrom;
    private String recipient;
    private String content;

    public Email(){}

    public Email(String setFrom, String recipient, String content) {
        this.setFrom = setFrom;
        this.recipient = recipient;
        this.content = content;
    }

    public String getSetFrom() {
        return setFrom;
    }

    public void setSetFrom(String setFrom) {
        this.setFrom = setFrom;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
