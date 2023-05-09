package edu.mirea.ardyc.umirea.data.model.chat;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    private List<ChatMessage> chatMessages = new ArrayList<>();

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public Chat(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public Chat() {
    }
}
