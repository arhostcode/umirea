package ru.ardyc.chat.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import ru.ardyc.chat.entity.MessageEntity;

import javax.persistence.Column;

@RegisterForReflection
public class Message {

    private String uuid;

    private String ownerUuid;

    private String groupUuid;

    private String message;


    public Message(String uuid, String ownerUuid, String groupUuid, String message) {
        this.uuid = uuid;
        this.ownerUuid = ownerUuid;
        this.groupUuid = groupUuid;
        this.message = message;
    }

    public static Message fromEntity(MessageEntity entity) {
        return new Message(entity.getUuid(), entity.getOwnerUuid(), entity.getGroupUuid(), entity.getMessage());
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }

    public void setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
