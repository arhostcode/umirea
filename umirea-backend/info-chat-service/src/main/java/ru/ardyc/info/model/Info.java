package ru.ardyc.info.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import ru.ardyc.info.entity.InfoEntity;

import javax.persistence.Column;

@RegisterForReflection
public class Info {

    private String name;

    private String uuid;

    private String ownerUuid;

    private String groupUuid;

    private String message;


    public Info(String name, String uuid, String ownerUuid, String groupUuid, String message) {
        this.name = name;
        this.uuid = uuid;
        this.ownerUuid = ownerUuid;
        this.groupUuid = groupUuid;
        this.message = message;
    }

    public static Info fromEntity(InfoEntity entity) {
        return new Info(entity.getName(), entity.getUuid(), entity.getOwnerUuid(), entity.getGroupUuid(), entity.getMessage());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
