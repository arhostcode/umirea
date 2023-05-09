package edu.mirea.ardyc.umirea.data.model.chat;

public class ChatMessage {

    private String uuid;
    private String ownerUuid;
    private String message;

    public ChatMessage(String uuid, String ownerUuid, String message) {
        this.uuid = uuid;
        this.ownerUuid = ownerUuid;
        this.message = message;
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }

    public String getMessage() {
        return message;
    }

    public String getUuid() {
        return uuid;
    }
}
