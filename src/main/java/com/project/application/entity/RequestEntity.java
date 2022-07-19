package com.project.application.entity;

public class RequestEntity {

    private int code;
    private String result;

    public RequestEntity(int code, String result){
        this.code = code;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
