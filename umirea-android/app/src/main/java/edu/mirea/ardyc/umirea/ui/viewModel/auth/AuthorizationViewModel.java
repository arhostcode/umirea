package edu.mirea.ardyc.umirea.ui.viewModel.auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.auth.User;
import edu.mirea.ardyc.umirea.data.model.net.DataResponse;

@HiltViewModel
public class AuthorizationViewModel extends ViewModel {
    private UserService userService;
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorText = new MutableLiveData<>();

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<String> getErrorText() {
        return errorText;
    }

    private boolean isLoggingIn = false;


    @Inject
    public AuthorizationViewModel(UserService userService) {
        this.userService = userService;
    }

    public void loginToServer(String login, String password) {
        if (isLoggingIn)
            return;

        isLoggingIn = true;
        new Thread(() -> {
            DataResponse<User> user = userService.login(login, password);
            if (user.getData() == null) {
                errorText.postValue(user.getMessage());
            } else {
                userMutableLiveData.postValue(user.getData());
                userService.saveUser(user.getData());
            }
            isLoggingIn = false;
        }).start();
    }

    public void clearErrorText() {
        errorText.postValue("");
    }
}