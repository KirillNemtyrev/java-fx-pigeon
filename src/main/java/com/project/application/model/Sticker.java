package com.project.application.model;

public class Sticker {
    private Long id;
    private String sticker;

    @Override
    public String toString(){
        return "{ \"id\" : " + id +
            ",\"sticker\" : \"" + sticker + "\" }";
    }
}
