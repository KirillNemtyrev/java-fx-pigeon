package com.project.application.model;

import com.project.application.api.Api;

import java.util.Date;

public class ThisMe {

    private Long id;
    private String name;
    private String username;
    private String uuid;
    private String email;
    private String aboutMe;
    private String fileNameAvatar;
    private Date createDate;

    public String getFileNameAvatar() {
        return Api.host + "resources/" + fileNameAvatar;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
