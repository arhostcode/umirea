package edu.mirea.ardyc.umirea.data.net.group;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GroupRemoteService {

    @GET("group/create")
    Call<JsonObject> createGroup(@Query("userToken") String userToken, @Query("groupTimeTable") String groupTimeTable, @Query("groupName") String groupName);


    @GET("group/connect")
    Call<JsonObject> connectGroup(@Query("userToken") String userToken, @Query("groupName") String groupName, @Query("groupToken") String groupToken);


    @GET("group/get/members")
    Call<JsonObject> getMembers(@Query("userToken") String userToken);


    @GET("group/disconnect")
    Call<JsonObject> disconnect(@Query("userToken") String userToken);

    @GET("group/get")
    Call<JsonObject> getGroup(@Query("userToken") String userToken);

    @POST("group/change/schedule")
    Call<JsonObject> changeSchedule(@Query("userToken") String userToken, @Query("groupSchedule") String groupSchedule);

    @POST("group/kick")
    Call<JsonObject> kick(@Query("userToken") String userToken, @Query("kickUserUuid") String kickUserUuid);
}
