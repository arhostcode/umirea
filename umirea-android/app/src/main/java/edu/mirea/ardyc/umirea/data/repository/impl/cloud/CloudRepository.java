package edu.mirea.ardyc.umirea.data.repository.impl.cloud;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.cloud.CloudDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.cloud.RemoteCloudDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.room.UmireaDatabase;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.model.net.DataResponse;
import edu.mirea.ardyc.umirea.data.net.cloud.CloudRemoteService;
import edu.mirea.ardyc.umirea.data.repository.Repository;
import retrofit2.Call;
import retrofit2.Response;

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
        return remoteDataSource.uploadFileInFolder(folderUuid, userToken, file);
    }

    public DataResponse<InputStream> downloadFile(String fileUuid, String userToken) {
        return remoteDataSource.downloadFile(fileUuid, userToken);
    }
}
