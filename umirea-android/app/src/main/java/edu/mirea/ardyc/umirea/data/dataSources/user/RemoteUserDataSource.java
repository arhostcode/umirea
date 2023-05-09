package edu.mirea.ardyc.umirea.data.dataSources.user;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.model.auth.User;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.net.auth.AuthRemoteService;
import retrofit2.Call;
import retrofit2.Response;

public class RemoteUserDataSource extends DataSource {

    private AuthRemoteService authService;

    public RemoteUserDataSource(Context context, AuthRemoteService authService) {
        super(context);
        this.authService = authService;
    }

    public DataResponse<User> auth(String login, String password) {
        try {
            User user;
            Call<JsonObject> loginCall = authService.login(login, password);
            Response<JsonObject> response = loginCall.execute();
            JsonElement code = response.body().get("code");
            JsonElement message = response.body().get("message");
            if (code.getAsInt() == 0) {
                user = new Gson().fromJson(message.getAsJsonObject(), User.class);
            } else {
                return new DataResponse<>(null, message.getAsString());
            }
            return new DataResponse<>(user);
        } catch (Exception e) {
            e.printStackTrace();
            return DataResponse.error("Не удалось авторизоваться");
        }
    }

    public DataResponse<User> register(String login, String password, String firstName, String lastName, String verificationCode) {
        try {
            User user;
            Call<JsonObject> loginCall = authService.register(login, password, firstName, lastName, verificationCode);
            Response<JsonObject> response = null;
            response = loginCall.execute();
            JsonElement code = response.body().get("code");
            JsonElement message = response.body().get("message");
            System.out.println(message.toString());
            if (code.getAsInt() == 0) {
                user = new Gson().fromJson(message.getAsJsonObject(), User.class);
            } else {
                return new DataResponse<>(null, message.getAsString());
            }
            return new DataResponse<>(user);
        } catch (Exception e) {
            return DataResponse.error("Не удалось зарегистрироваться");
        }
    }

    public DataResponse<User> getInfo(String token) {
        try {
            User user = null;
            Call<JsonObject> loginCall = authService.getInfo(token);
            Response<JsonObject> response = loginCall.execute();
            JsonElement code = response.body().get("code");
            JsonElement message = response.body().get("message");
            if (code.getAsInt() == 0) {
                user = new Gson().fromJson(message.getAsJsonObject(), User.class);
            }
            return new DataResponse<>(user);
        } catch (Exception e) {
            return DataResponse.error("Не удалось получить информацию");
        }
    }
}
