package edu.mirea.ardyc.umirea.data.dataSources.room.info.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;

@Entity(tableName = "info")
public class InfoEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Long id;


    @ColumnInfo(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "owner_id")
    private String ownerId;

    public InfoEntity() {
    }

    @Ignore
    public InfoEntity(String uuid, String name, String text, String ownerId) {
        this.uuid = uuid;
        this.name = name;
        this.text = text;
        this.ownerId = ownerId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static InfoEntity fromInfo(InfoMessage infoMessage) {
        return new InfoEntity(infoMessage.getUuid(), infoMessage.getName(), infoMessage.getMessage(), infoMessage.getOwner());
    }

    public InfoMessage toInfo() {
        return new InfoMessage(uuid, name, text, ownerId);
    }
}
