package edu.mirea.ardyc.umirea.ui.viewModel.auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.auth.User;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.repository.impl.auth.UserRepository;

@HiltViewModel
public class AuthorizationViewModel extends ViewModel {
    private UserRepository userRepository;
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
    public AuthorizationViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void loginToServer(String login, String password) {
        if (isLoggingIn)
            return;

        isLoggingIn = true;
        new Thread(() -> {
            DataResponse<User> user = userRepository.auth(login, password);
            if (user.getData() == null) {
                errorText.postValue("Пользователь не найден");
            } else {
                userMutableLiveData.postValue(user.getData());
                userRepository.saveUser(user.getData());
            }
            isLoggingIn = false;
        }).start();
    }

    public void clearErrorText() {
        errorText.postValue("");
    }
}