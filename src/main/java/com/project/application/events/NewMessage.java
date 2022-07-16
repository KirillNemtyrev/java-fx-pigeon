package com.project.application.events;

public class NewMessage {

    private int event;
    private Long chatId;
    private String text;
    private Long userId;
    private Long sendDate;
    private Long newCountMessages;

    public NewMessage(Long chatId, Long userId, Long sendDate, Long newCountMessages, String text) {
        this.chatId = chatId;
        this.userId = userId;
        this.sendDate = sendDate;
        this.newCountMessages = newCountMessages;
        this.text = text;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }

    public Long getSendDate() {
        return sendDate;
    }

    public Long getNewCountMessages() {
        return newCountMessages;
    }

    public Long getUserId() {
        return userId;
    }

    public void setNewCountMessages(Long newCountMessages) {
        this.newCountMessages = newCountMessages;
    }

    public void setSendDate(Long sendDate) {
        this.sendDate = sendDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
