package com.project.application.model;

import com.project.application.api.Api;

public class ChatModel {

    private Long id;
    private Long userId;
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
        return Api.host + "resources/avatar/" + fileNameAvatar;
    }

    public String getLastMessage(){
        return lastMessage;
    }

}
