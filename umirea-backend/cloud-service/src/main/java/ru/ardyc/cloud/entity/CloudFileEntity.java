package ru.ardyc.cloud.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class CloudFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String folderId;

    private String uuid;

    @ManyToOne
    @JoinColumn
    private CloudFolderEntity parent;

    public CloudFileEntity() {
    }

    public CloudFileEntity(String name, String folderId, String uuid) {
        this.name = name;
        this.folderId = folderId;
        this.uuid = uuid;
    }

    public CloudFileEntity(String name, String folderId, String uuid, CloudFolderEntity parent) {
        this.name = name;
        this.folderId = folderId;
        this.uuid = uuid;
        this.parent = parent;
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

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public CloudFolderEntity getParent() {
        return parent;
    }

    public void setParent(CloudFolderEntity parent) {
        this.parent = parent;
    }
}
