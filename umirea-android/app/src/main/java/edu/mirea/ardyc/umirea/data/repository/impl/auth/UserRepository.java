package edu.mirea.ardyc.umirea.data.repository.impl.auth;

import android.content.Context;

import javax.inject.Inject;

import edu.mirea.ardyc.umirea.data.dataSources.user.RemoteUserDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.user.UserDataSource;
import edu.mirea.ardyc.umirea.data.model.auth.User;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.repository.Repository;


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
        try {
            DataResponse<User> response = remoteUserDataSource.auth(username, password);
            return response;
        } catch (Exception e) {
            return new DataResponse<>(null, "Ошибка при авторизации. Попробуйте еще раз");
        }
    }

    public DataResponse<User> register(String login, String password, String firstName, String lastName, String verificationCode) {
        try {
            return remoteUserDataSource.register(login, password, firstName, lastName, verificationCode);
        } catch (Exception e) {
            return new DataResponse<>(null, "Ошибка при регистрации. Попробуйте еще раз");
        }
    }

    public DataResponse<User> getInfo(String token) {
        try {
            DataResponse<User> response = remoteUserDataSource.getInfo(token);
            if (response.isError()) {
                return new DataResponse<>(userDataSource.loadUser());
            }
            saveUser(response.getData());
            return response;
        }catch (Exception e) {
            return new DataResponse<>(null, "Ошибка при получении информации");
        }
    }

    public void saveUser(User user) {
        userDataSource.saveUser(user);
    }

    public void removeUser() {
        userDataSource.removeUser();
    }


}
