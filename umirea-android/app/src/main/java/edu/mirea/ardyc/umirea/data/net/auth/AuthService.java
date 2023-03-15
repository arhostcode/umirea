package edu.mirea.ardyc.umirea.data.net.auth;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthService {
    @GET("auth/login")
    Call<JsonObject> login(@Query("login") String login, @Query("password") String password);

    @GET("auth/register")
    Call<JsonObject> register(@Query("login") String login, @Query("password") String password, @Query("firstName") String firstName, @Query("lastName") String lastName, @Query("verificationCode") String verificationCode);

    @GET("auth/verify")
    Call<JsonObject> verify(@Query("login") String login, @Query("password") String password, @Query("firstName") String firstName, @Query("lastName") String lastName);


}
