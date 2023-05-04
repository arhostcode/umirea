package edu.mirea.ardyc.umirea.data.net.cloud;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface CloudRemoteService {

    @Multipart
    @POST("cloud/folder/upload")
    Call<JsonObject> uploadFileInFolder(@Part("file_name") RequestBody fileName, @Part("folder_uuid") RequestBody folderUuid, @Part("user_token") RequestBody userToken, @Part MultipartBody.Part filePart);

    @POST("cloud/folder/create")
    Call<JsonObject> createFolder(@Query("userToken") String userToken, @Query("folderName") String folderName);

    @GET("cloud/folder/get")
    Call<JsonObject> getFolders(@Query("userToken") String userToken);

    @GET("cloud/file/get")
    @Streaming
    Call<ResponseBody> downloadFile(@Query("userToken") String userToken, @Query("fileUuid") String fileUuid);


}
