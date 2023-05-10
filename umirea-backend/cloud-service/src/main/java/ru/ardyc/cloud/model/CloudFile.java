package ru.ardyc.cloud.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import ru.ardyc.cloud.entity.CloudFileEntity;

@RegisterForReflection
public class CloudFile {

    private String name;

    private String folderId;

    private String uuid;

    public CloudFile(String name, String folderId, String uuid) {
        this.name = name;
        this.folderId = folderId;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getFolderId() {
        return folderId;
    }

    public String getUuid() {
        return uuid;
    }

    public static CloudFile fromEntity(CloudFileEntity entity) {
        return new CloudFile(entity.getName(), entity.getFolderId(), entity.getUuid());
    }
}
