package edu.mirea.ardyc.umirea.data.net.auth;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DashboardService {

    @GET("schedule/get/group/base")
    Call<JsonObject> getBaseGroupSchedule(@Query("groupName") String groupName);


}
