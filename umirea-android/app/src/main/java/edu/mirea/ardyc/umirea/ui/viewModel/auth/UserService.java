package edu.mirea.ardyc.umirea.ui.viewModel.auth;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import edu.mirea.ardyc.umirea.data.model.auth.User;
import edu.mirea.ardyc.umirea.data.model.net.DataResponse;
import edu.mirea.ardyc.umirea.data.repository.impl.auth.UserRepository;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;

public class UserService {

    private UserRepository authRepository;


    private Context context;

    @Inject
    public UserService(Context context, UserRepository authRepository) {
        this.authRepository = authRepository;
        this.context = context;
    }

    public DataResponse<User> login(String username, String password) {
        try {
            return authRepository.auth(username, password);
        } catch (Exception e) {
            return new DataResponse<>(null, "Ошибка авторизации");
        }
    }

    public DataResponse<User> register(String login, String password, String firstName, String lastName, String verificationCode) {
        try {
            return authRepository.register(login, password, firstName, lastName, verificationCode);
        } catch (Exception e) {
            return new DataResponse<>(null, "Ошибка регистрации");
        }
    }

    public DataResponse<User> getInfo(String token) {
        try {
            return authRepository.getInfo(token);
        } catch (Exception e) {
            return null;
        }
    }


    public void saveUser(User user) {
        authRepository.saveUser(user);
    }

    public void removeUser() {
        authRepository.removeUser();
    }


}