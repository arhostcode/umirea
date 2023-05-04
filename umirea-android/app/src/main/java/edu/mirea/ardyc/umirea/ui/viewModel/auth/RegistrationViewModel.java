package edu.mirea.ardyc.umirea.ui.viewModel.auth;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.auth.User;
import edu.mirea.ardyc.umirea.data.model.net.DataResponse;

@HiltViewModel
public class RegistrationViewModel extends AndroidViewModel {

    private UserService userService;
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorText = new MutableLiveData<>();

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<String> getErrorText() {
        return errorText;
    }

    private boolean isRegistering = false;

    @Inject
    public RegistrationViewModel(Application application, UserService userService) {
        super(application);
        this.userService = userService;
    }

    public void register(String login, String password, String firstName, String lastName, String verificationCode) {
        if (isRegistering) {
            return;
        }
        isRegistering = true;
        new Thread(() -> {
            DataResponse<User> response = userService.register(login, password, firstName, lastName, verificationCode);
            if (response.getData() != null) {
                userMutableLiveData.postValue(response.getData());
                userService.saveUser(response.getData());
            } else {
                errorText.postValue(response.getMessage());
            }
            isRegistering = false;
        }).start();
    }

    public void verify(String login, String password, String firstName, String lastName) {
//        Call<JsonObject> loginCall = authService.verify(login, password, firstName, lastName);
//        loginCall.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                JsonElement code = response.body().get("code");
//                JsonElement message = response.body().get("message");
//                if (code.getAsInt() != 0) {
//                    errorText.postValue(message.getAsString());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                System.out.println(t.getMessage());
//            }
//        });
    }


}