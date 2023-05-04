package ru.ardyc.auth.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.ardyc.auth.entity.UserEntity;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User {
    private String firstName;
    private String lastName;
    private String login;
    private String educationGroup;
    private String token;
    private String uniqueID;

    public User(String firstName, String lastName, String login, String educationGroup, String token, String uniqueID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.educationGroup = educationGroup;
        this.token = token;
        this.uniqueID = uniqueID;
    }

    public static User fromEntity(UserEntity entity) {
        return new User(entity.getFirstName(), entity.getLastName(), entity.getLogin(), entity.getEducationGroup(), entity.getToken(), entity.getUniqueID());
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getEducationGroup() {
        return educationGroup;
    }

    public String getToken() {
        return token;
    }

    public String getUniqueID() {
        return uniqueID;
    }

}
