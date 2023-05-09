package edu.mirea.ardyc.umirea.data.dataSources.room.cloud.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import edu.mirea.ardyc.umirea.data.model.cloud.CloudFile;

@Entity(
        foreignKeys = @ForeignKey(entity = CloudFolderEntity.class,
                parentColumns = "uuid",
                childColumns = "folder_uuid",
                onDelete = ForeignKey.CASCADE),
        tableName = "cloud_file")
public class CloudFileEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uuid")
    public String uuid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "folder_uuid")
    public String folderUuid;

    public CloudFileEntity() {
    }

    public CloudFileEntity(String uuid, String name, String folderUuid) {
        this.uuid = uuid;
        this.name = name;
        this.folderUuid = folderUuid;
    }

    public static CloudFileEntity fromModel(CloudFile cloudFile) {
        return new CloudFileEntity(cloudFile.getUuid(), cloudFile.getName(), cloudFile.getFolderId());
    }

    public CloudFile toModel() {
        return new CloudFile(uuid, name, folderUuid);
    }

}
