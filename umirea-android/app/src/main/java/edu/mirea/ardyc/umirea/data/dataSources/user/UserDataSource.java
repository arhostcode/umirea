package edu.mirea.ardyc.umirea.data.dataSources.user;

import android.content.Context;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.model.auth.User;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;

public class UserDataSource extends DataSource {

    public UserDataSource(Context context) {
        super(context);
    }

    public void saveUser(User user) {
        context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).edit()
                .putString("user_firstName", user.getFirstName())
                .putString("user_lastName", user.getLastName())
                .putString("user_token", user.getToken())
                .putString("user_educationGroup", user.getEducationGroup())
                .putString("user_uuid", user.getUniqueID())
                .putString("user_login", user.getLogin())
                .apply();
    }

    public void removeUser() {
        context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).edit()
                .remove("user_firstName")
                .remove("user_lastName")
                .remove("user_token")
                .remove("user_educationGroup")
                .remove("user_uuid")
                .remove("user_login")
                .apply();
    }

    public User loadUser() {
        String login = context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_login", "null");
        String uuid = context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_uuid", "null");
        String firstName = context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_firstName", "null");
        String lastName = context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_lastName", "null");
        String educationGroup = context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_educationGroup", "null");
        String token = context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "null");
        return new User(firstName, lastName, login, educationGroup, token, uuid);
    }

}
