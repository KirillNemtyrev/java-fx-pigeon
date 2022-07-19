package com.project.application.entity;

import com.project.application.model.StickerList;

public class StickerEntity {

    private StickerList stickerList;
    private float value;

    public StickerList getStickerList() {
        return stickerList;
    }

    public void setStickerList(StickerList stickerList) {
        this.stickerList = stickerList;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
