package edu.mirea.ardyc.umirea.data.net.chat;

import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChatRemoteService {

    @POST("chat/send")
    Call<JsonObject> sendMessage(@Query("userToken") String userToken, @Body RequestBody text);

    @GET("chat/get")
    Call<JsonObject> getMessages(@Query("userToken") String userToken, @Query("after") String afterUuid);

}
