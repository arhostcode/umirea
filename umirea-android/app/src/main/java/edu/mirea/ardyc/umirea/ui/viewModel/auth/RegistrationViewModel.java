package edu.mirea.ardyc.umirea.ui.viewModel.auth;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.auth.User;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.repository.impl.auth.UserRepository;

@HiltViewModel
public class RegistrationViewModel extends AndroidViewModel {

    private UserRepository userRepository;
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
    public RegistrationViewModel(Application application, UserRepository userRepository) {
        super(application);
        this.userRepository = userRepository;
    }

    public void register(String login, String password, String firstName, String lastName, String verificationCode) {
        if (isRegistering) {
            return;
        }
        isRegistering = true;
        new Thread(() -> {
            DataResponse<User> response = userRepository.register(login, password, firstName, lastName, verificationCode);
            if (response.getData() != null) {
                userMutableLiveData.postValue(response.getData());
                userRepository.saveUser(response.getData());
            } else {
                errorText.postValue(response.getMessage());
            }
            isRegistering = false;
        }).start();
    }


}