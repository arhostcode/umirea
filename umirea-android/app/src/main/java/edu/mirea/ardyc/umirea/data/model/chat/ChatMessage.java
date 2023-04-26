package edu.mirea.ardyc.umirea.data.model.chat;

import java.util.UUID;

import edu.mirea.ardyc.umirea.data.model.group.Member;

public class ChatMessage {

    private String sender;
    private String message;

    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
