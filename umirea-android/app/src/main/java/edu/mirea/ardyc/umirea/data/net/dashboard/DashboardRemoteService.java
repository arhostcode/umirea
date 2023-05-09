package edu.mirea.ardyc.umirea.data.net.dashboard;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DashboardRemoteService {

    @GET("schedule/get/group/base")
    Call<JsonObject> getBaseGroupSchedule(@Query("groupName") String groupName);

    @GET("schedule/get/user/base")
    Call<JsonObject> getBaseUserSchedule(@Query("userToken") String userToken);


    @GET("schedule/get/groups/all")
    Call<JsonObject> getAllGroups();

    @GET("schedule/user/lessons")
    Call<JsonObject> getUserLessons(@Query("userToken") String userToken);


    @GET("schedule/user/lessons/add")
    Call<JsonObject> addUserLesson(@Query("userToken") String userToken, @Query("day") Integer day, @Query("month") Integer month, @Query("year") Integer year, @Query("name") String name, @Query("teacherName") String teacherName, @Query("room") String room, @Query("lessonTime") Integer lessonTime, @Query("lessonType") Integer lessonType);


    @GET("schedule/user/homeworks")
    Call<JsonObject> getUserHomeworks(@Query("userToken") String userToken);


    @GET("schedule/user/homeworks/add")
    Call<JsonObject> addUserHomework(@Query("userToken") String userToken, @Query("day") Integer day, @Query("month") Integer month, @Query("year") Integer year, @Query("lessonTime") Integer lessonTime, @Query("text") String text);


    @GET("schedule/user/notes")
    Call<JsonObject> getUserNotes(@Query("userToken") String userToken);


    @GET("schedule/user/notes/add")
    Call<JsonObject> addUserNote(@Query("userToken") String userToken, @Query("day") Integer day, @Query("month") Integer month, @Query("year") Integer year, @Query("lessonTime") Integer lessonTime, @Query("text") String text);


}
