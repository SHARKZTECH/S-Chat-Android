package com.example.schat;

public class Message {
    private String messageId;
    private String uId;
    private String text;
    private Long createdAt;

    public Message() {
    }

    public Message(String uId, String text) {
        this.uId = uId;
        this.text = text;
    }

    public Message(String uId, String text, Long createdAt) {
        this.uId = uId;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
