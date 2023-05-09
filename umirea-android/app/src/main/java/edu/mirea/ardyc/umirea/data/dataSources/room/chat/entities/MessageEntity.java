package edu.mirea.ardyc.umirea.data.dataSources.room.chat.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.mirea.ardyc.umirea.data.model.chat.ChatMessage;

@Entity(tableName = "chat")
public class MessageEntity {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "owner_id")
    private String ownerId;

    public MessageEntity(String uuid, String text, String ownerId) {
        this.uuid = uuid;
        this.text = text;
        this.ownerId = ownerId;
    }

    public MessageEntity() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public ChatMessage toModel() {
        return new ChatMessage(uuid, ownerId, text);
    }

    public static MessageEntity fromModel(ChatMessage chatMessage) {
        return new MessageEntity(chatMessage.getUuid(), chatMessage.getMessage(), chatMessage.getOwnerUuid());
    }
}
