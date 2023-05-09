package edu.mirea.ardyc.umirea.data.dataSources.cloud;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.model.cloud.CloudFolder;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.net.cloud.CloudRemoteService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class RemoteCloudDataSource extends DataSource {

    public CloudRemoteService cloudRemoteService;

    public RemoteCloudDataSource(Context context, CloudRemoteService cloudRemoteService) {
        super(context);
        this.cloudRemoteService = cloudRemoteService;
    }

    public DataResponse<List<CloudFolder>> getData(String userToken) {
        List<CloudFolder> cloudFolders = null;
        Call<JsonObject> foldersCall = cloudRemoteService.getFolders(userToken);
        try {
            Response<JsonObject> response = foldersCall.execute();
            JsonElement code = response.body().get("code");
            JsonElement message = response.body().get("message");
            if (code.getAsInt() == 0) {
                cloudFolders = new Gson().fromJson(message, new TypeToken<List<CloudFolder>>() {
                }.getType());
                return new DataResponse<>(cloudFolders);
            }
            return new DataResponse<>(null, message.getAsString());
        } catch (Exception e) {
            return new DataResponse<>(null, "Ошибка загрузке файлов");
        }
    }

    public DataResponse<List<CloudFolder>> uploadFileInFolder(String folderUuid, String userToken, File file) {
        try {
            RequestBody fileNameRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());
            RequestBody folderUuidRequest = RequestBody.create(MediaType.parse("multipart/form-data"), folderUuid);
            RequestBody userTokenRequest = RequestBody.create(MediaType.parse("multipart/form-data"), userToken);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));

            Call<JsonObject> foldersCall = cloudRemoteService.uploadFileInFolder(fileNameRequest, folderUuidRequest, userTokenRequest, filePart);

            Response<JsonObject> response = foldersCall.execute();
            JsonElement code = response.body().get("code");
            JsonElement message = response.body().get("message");
            if (code.getAsInt() == 0) {
                return getData(userToken);
            }
            return new DataResponse<>(null, message.getAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(null, "Ошибка загрузки файлов");
        }
    }


    public DataResponse<InputStream> downloadFile(String fileUuid, String userToken) {
        try {
            Call<ResponseBody> downloadFileCall = cloudRemoteService.downloadFile(userToken, fileUuid);
            Response<ResponseBody> response = downloadFileCall.execute();
            return new DataResponse<>(response.body().byteStream());
        } catch (Exception e) {
            return new DataResponse<>(null, "Ошибка загрузки файла");
        }
    }

    public DataResponse<CloudFolder> createFolder(String userToken, String name) {
        try {
            Call<JsonObject> createFolderCall = cloudRemoteService.createFolder(userToken, name);
            Response<JsonObject> response = createFolderCall.execute();
            JsonElement code = response.body().get("code");
            JsonElement message = response.body().get("message");
            if (code.getAsInt() == 0) {
                return new DataResponse<>(new Gson().fromJson(message, CloudFolder.class));
            }
            return new DataResponse<>(null, message.getAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(null, "Ошибка создания папки");
        }
    }
}
