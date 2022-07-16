package com.project.application.entity;

import com.project.application.draw.ChatDraw;
import com.project.application.model.ChatModel;

public class ChatEntity {

    private ChatModel chatModel;
    private ChatDraw chatDraw;

    public ChatEntity() {}

    public ChatEntity(ChatModel chatModel, ChatDraw chatDraw) {
        this.chatModel = chatModel;
        this.chatDraw = chatDraw;
    }

    public ChatModel getChatModel() {
        return chatModel;
    }

    public ChatDraw getChatDraw() {
        return chatDraw;
    }
}
