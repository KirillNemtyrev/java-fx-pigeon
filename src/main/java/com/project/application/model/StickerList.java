package com.project.application.model;

import java.util.List;

public class StickerList {

    private Long id;
    private String name;
    private List<Sticker> stickers;

    @Override
    public String toString(){
        return "{ \"id\" : " + id +
                ",\"name\" : " + name +
                ",\"stickers\" : \"" + stickers + "\" }";
    }

}
