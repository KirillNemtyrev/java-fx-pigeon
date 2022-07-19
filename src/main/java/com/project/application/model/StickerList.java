package com.project.application.model;

import java.util.List;

public class StickerList {

    private Long id;
    private String name;
    private String icon;
    private List<Sticker> stickers;

    public List<Sticker> getStickers(){
        return stickers;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return "{ \"id\" : " + id +
                ",\"name\" : " + name +
                ",\"stickers\" : \"" + stickers + "\" }";
    }

    public String getIcon() {
        return icon;
    }
}
