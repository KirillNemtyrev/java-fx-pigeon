package com.project.application.model;

import com.project.application.api.Api;

public class ChatModel {

    private Long id;
    private Long userId;
    private Long lastSenderId;
    private Long lastMessageDate;
    private Long countNewMessage;

    private String username;
    private String name;
    private String fileNameAvatar;
    private String lastMessage;

    @Override
    public String toString(){
        return "{ \"id\": " + id + ", " +
                "\"userId\": " + userId + ", " +
                "\"lastMessageDate\": " + lastMessageDate + ", " +
                "\"countNewMessage\": " + countNewMessage + ", " +
                "\"username\": " + "\"" + username + "\"," +
                "\"name\": " + "\"" + name + "\"," +
                "\"fileNameAvatar\": " + "\"" + fileNameAvatar + "\"," +
                "\"lastMessage\": " + "\"" + lastMessage + "\" }";
    }

    public Long getUserId() {
        return userId;
    }

    public Long getId(){
        return id;
    }

    public Long getLastMessageDate(){
        return lastMessageDate;
    }

    public String getUsername(){
        return username;
    }

    public String getName(){
        return name;
    }

    public String getFileNameAvatar(){
        return Api.host + "resources/" + fileNameAvatar;
    }

    public String getLastMessage(){
        return lastMessage;
    }

    public Long getLastSenderId() {
        return lastSenderId;
    }

    public Long getCountNewMessage(){
        return countNewMessage;
    }

    public void setLastSenderId(Long lastSenderId) {
        this.lastSenderId = lastSenderId;
    }

    public void setLastMessageDate(Long lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public void setCountNewMessage(Long countNewMessage) {
        this.countNewMessage = countNewMessage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFileNameAvatar(String fileNameAvatar) {
        this.fileNameAvatar = fileNameAvatar;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
