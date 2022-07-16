package com.project.application.events;

import java.util.Date;

public class SendMessage {

    private int event;
    private Long chatId;
    private Long userId;
    private String text;
    private Long sendDate;

    public SendMessage(Long chatId, Long userId, Long sendDate, String text){
        this.chatId = chatId;
        this.userId = userId;
        this.sendDate = sendDate;
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getSendDate() {
        return sendDate;
    }

    public void setSendDate(Long sendDate) {
        this.sendDate = sendDate;
    }
}
