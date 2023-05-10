package ru.ardyc.cloud.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import ru.ardyc.cloud.entity.CloudFileEntity;
import ru.ardyc.cloud.entity.CloudFolderEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
@RegisterForReflection
public class CloudFolder {
    private String uuid;

    private String name;

    private String groupUuid;

    @OneToMany
    private List<CloudFileEntity> files;

    public String getUuid() {
        return uuid;
    }


    public CloudFolder(String uuid, String name, String groupUuid, List<CloudFileEntity> files) {
        this.uuid = uuid;
        this.name = name;
        this.groupUuid = groupUuid;
        this.files = files;
    }

    public CloudFolder(String uuid, String name, String groupUuid) {
        this.uuid = uuid;
        this.name = name;
        this.groupUuid = groupUuid;
        this.files = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public List<CloudFileEntity> getFiles() {
        return files;
    }

    public static CloudFolder fromEntity(CloudFolderEntity entity) {
        return new CloudFolder(entity.getUuid(), entity.getName(), entity.getGroupUuid(), entity.getFiles());
    }

}
