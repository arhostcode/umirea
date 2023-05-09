package edu.mirea.ardyc.umirea.data.repository.impl.cloud;

import android.content.Context;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.cloud.CloudDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.cloud.RemoteCloudDataSource;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.repository.Repository;

public class CloudRepository extends Repository {


    private CloudDataSource cloudDataSource;
    private RemoteCloudDataSource remoteDataSource;

    public CloudRepository(Context context, CloudDataSource cloudDataSource, RemoteCloudDataSource remoteDataSource) {
        super(context);
        this.cloudDataSource = cloudDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public DataResponse<List<CloudFolder>> getData(String userToken) {
        DataResponse<List<CloudFolder>> cloudFolders = remoteDataSource.getData(userToken);
        if (!cloudFolders.isError())
            cloudDataSource.saveData(cloudFolders.getData());
        return cloudFolders;
    }

    public DataResponse<List<CloudFolder>> loadLocalFolders() {
        DataResponse<List<CloudFolder>> cloudFolders = cloudDataSource.getData();
        return cloudFolders;
    }


    public void saveData(List<CloudFolder> cloudFolders) {
        cloudDataSource.saveData(cloudFolders);
    }

    public DataResponse<List<CloudFolder>> uploadFileInFolder(String folderUuid, String userToken, File file) {
        DataResponse<List<CloudFolder>> response = remoteDataSource.uploadFileInFolder(folderUuid, userToken, file);
        if (!response.isError())
            saveData(response.getData());
        return response;
    }

    public DataResponse<InputStream> downloadFile(String fileUuid, String userToken) {
        return remoteDataSource.downloadFile(fileUuid, userToken);
    }

    public DataResponse<CloudFolder> createFolder(String userToken, String name) {
        return remoteDataSource.createFolder(userToken, name);
    }

    public void clear() {
        cloudDataSource.clear();
    }
}
