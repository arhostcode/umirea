package edu.mirea.ardyc.umirea.data.dataSources.room.cloud.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import java.util.stream.Collectors;

import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;

public class FileWithFolder {
    @Embedded
    public CloudFolderEntity folder;
    @Relation(parentColumn = "uuid", entity = CloudFileEntity.class, entityColumn = "folder_uuid")
    public List<CloudFileEntity> files;

    public CloudFolder toModel() {
        return new CloudFolder(folder.name, folder.uuid, folder.groupUUID, files.stream().map(CloudFileEntity::toModel).collect(Collectors.toList()));
    }

}
