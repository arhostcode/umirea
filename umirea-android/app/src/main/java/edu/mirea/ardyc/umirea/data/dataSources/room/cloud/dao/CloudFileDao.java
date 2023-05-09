package edu.mirea.ardyc.umirea.data.dataSources.room.cloud.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.room.cloud.entities.CloudFileEntity;
import edu.mirea.ardyc.umirea.data.dataSources.room.cloud.entities.CloudFolderEntity;
import edu.mirea.ardyc.umirea.data.dataSources.room.cloud.entities.FileWithFolder;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFile;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;

@Dao
public abstract class CloudFileDao {

    @Transaction
    @Query("SELECT * FROM cloud_folders")
    public abstract List<FileWithFolder> getFolders();

    @Transaction
    public void updateFiles(List<CloudFolder> cloudFolders) {
        clearAll();
        for (CloudFolder folder : cloudFolders) {
            insertFolder(folder);
        }
    }

    @Transaction
    public void insertFolder(CloudFolder folder) {
        insertFolder(CloudFolderEntity.fromModel(folder));
        for (CloudFile file : folder.getFiles()) {
            insertFile(CloudFileEntity.fromModel(file));
        }
    }

    @Insert
    public abstract void insertFolder(CloudFolderEntity folder);

    @Insert
    public abstract void insertFile(CloudFileEntity file);


    @Transaction
    public void clearAll() {
        clearFolders();
        clearFiles();
    }

    @Query("DELETE FROM cloud_folders")
    public abstract void clearFolders();

    @Query("DELETE FROM cloud_file")
    public abstract void clearFiles();

}
