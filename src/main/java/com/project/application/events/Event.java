package com.project.application.events;

public class Event {

    private int event;
    private Long userId;
    private Long chatId;
    private String text;
    private Long sendDate;
    private Long newCountMessages;

    public int getEvent() {
        return event;
    }
}
