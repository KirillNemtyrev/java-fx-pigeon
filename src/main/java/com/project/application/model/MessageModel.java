package com.project.application.model;

public class MessageModel {
    private Long id;
    private Long chatId;
    private Long senderId;
    private String text;
    private Long date;

    public String getText() {
        return text;
    }

    public Long getDate() {
        return date;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getChatId() {
        return chatId;
    }

    public Long getId() {
        return id;
    }
}
