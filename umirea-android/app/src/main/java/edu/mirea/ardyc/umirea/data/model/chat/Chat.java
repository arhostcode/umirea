package edu.mirea.ardyc.umirea.data.model.chat;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    private List<ChatMessage> chatMessages = new ArrayList<>();

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void addMessage(ChatMessage message) {
        chatMessages.add(message);
    }
}
