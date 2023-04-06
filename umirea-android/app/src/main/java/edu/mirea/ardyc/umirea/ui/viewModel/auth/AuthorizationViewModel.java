package edu.mirea.ardyc.umirea.ui.viewModel.auth;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.mirea.ardyc.umirea.data.model.auth.User;
import edu.mirea.ardyc.umirea.data.net.auth.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthorizationViewModel extends ViewModel {

    private String authServer;
    private AuthService authService;
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorText = new MutableLiveData<>();

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<String> getErrorText() {
        return errorText;
    }

    public void init() {
        authServer = "http://192.168.118.234:8081";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(authServer)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        authService = retrofit.create(AuthService.class);
    }

    public void loginToServer(String login, String password) {
        Call<JsonObject> loginCall = authService.login(login, password);
        loginCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                JsonElement code = response.body().get("code");
                JsonElement message = response.body().get("message");
                if (code.getAsInt() == 0) {
                    User user = new Gson().fromJson(message.getAsJsonObject(), User.class);
                    userMutableLiveData.postValue(user);
                } else {
                    errorText.postValue(message.getAsString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                System.out.println(t.getMessage());
            }
        });
    }

}