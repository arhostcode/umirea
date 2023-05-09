package edu.mirea.ardyc.umirea.data.dataSources.room.cloud.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.inject.Inject;

import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;

@Entity(tableName = "cloud_folders")
public class CloudFolderEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uuid")
    public String uuid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "group_uuid")
    public String groupUUID;

    public CloudFolderEntity() {
    }

    @Inject
    public CloudFolderEntity(String name, String uuid, String groupUUID) {
        this.name = name;
        this.uuid = uuid;
        this.groupUUID = groupUUID;
    }

    public static CloudFolderEntity fromModel(CloudFolder model) {
        return new CloudFolderEntity(model.getName(), model.getUuid(), model.getGroupUuid());
    }

    public CloudFolder toModel() {
        return new CloudFolder(name, uuid, groupUUID);
    }

}
