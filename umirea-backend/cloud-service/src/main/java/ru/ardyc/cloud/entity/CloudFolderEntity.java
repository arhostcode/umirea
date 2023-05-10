package ru.ardyc.cloud.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CloudFolderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;

    private String name;

    private String groupUuid;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CloudFileEntity> files;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public CloudFolderEntity() {
    }

    public CloudFolderEntity(String uuid, String name, String groupUuid, List<CloudFileEntity> files) {
        this.uuid = uuid;
        this.name = name;
        this.groupUuid = groupUuid;
        this.files = files;
    }

    public CloudFolderEntity(String uuid, String name, String groupUuid) {
        this.uuid = uuid;
        this.name = name;
        this.groupUuid = groupUuid;
        this.files = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public List<CloudFileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<CloudFileEntity> files) {
        this.files = files;
    }

}
