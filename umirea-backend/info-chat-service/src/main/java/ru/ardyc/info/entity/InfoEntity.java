package ru.ardyc.info.entity;

import javax.persistence.*;

@Entity
public class InfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String uuid;

    private String ownerUuid;

    private String groupUuid;

    @Column(columnDefinition="TEXT", length = 2100)
    private String message;

    public InfoEntity(String name, String uuid, String ownerUuid, String groupUuid, String message) {
        this.name = name;
        this.uuid = uuid;
        this.ownerUuid = ownerUuid;
        this.groupUuid = groupUuid;
        this.message = message;
    }

    public InfoEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
