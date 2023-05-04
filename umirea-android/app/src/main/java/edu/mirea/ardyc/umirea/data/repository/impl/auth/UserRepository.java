package edu.mirea.ardyc.umirea.data.repository.impl.auth;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

import javax.inject.Inject;

import edu.mirea.ardyc.umirea.data.dataSources.user.RemoteUserDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.user.UserDataSource;
import edu.mirea.ardyc.umirea.data.model.auth.User;
import edu.mirea.ardyc.umirea.data.model.net.DataResponse;
import edu.mirea.ardyc.umirea.data.net.auth.AuthRemoteService;
import edu.mirea.ardyc.umirea.data.repository.RemoteRepository;
import edu.mirea.ardyc.umirea.data.repository.Repository;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import retrofit2.Call;
import retrofit2.Response;


public class UserRepository extends Repository {

    private UserDataSource userDataSource;
    private RemoteUserDataSource remoteUserDataSource;

    @Inject
    public UserRepository(Context context, UserDataSource userDataSource, RemoteUserDataSource remoteUserDataSource) {
        super(context);
        this.userDataSource = userDataSource;
        this.remoteUserDataSource = remoteUserDataSource;
    }


    public DataResponse<User> auth(String username, String password) {
        DataResponse<User> response = remoteUserDataSource.auth(username, password);
        return response;
    }

    public DataResponse<User> register(String login, String password, String firstName, String lastName, String verificationCode) {
        return remoteUserDataSource.register(login, password, firstName, lastName, verificationCode);
    }

    public DataResponse<User> getInfo(String token) {
        DataResponse<User> response = remoteUserDataSource.getInfo(token);
        if (response.isError()) {
            return new DataResponse<>(userDataSource.loadUser());
        }
        saveUser(response.getData());
        return response;
    }

    public void saveUser(User user) {
        userDataSource.saveUser(user);
    }

    public void removeUser() {
        userDataSource.removeUser();
    }


}
