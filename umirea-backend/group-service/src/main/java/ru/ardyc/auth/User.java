package ru.ardyc.auth;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.ardyc.response.TypedMessageResponse;
import ru.ardyc.utils.JsonUtils;

import javax.json.JsonObject;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User {
    private String firstName;
    private String lastName;
    private String login;
    private String educationGroup;
    private String token;

    private String imageId;

    private String uniqueID;

    private String role;

    public User(String firstName, String lastName, String login, String educationGroup, String token, String imageId, String uniqueID, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.educationGroup = educationGroup;
        this.token = token;
        this.imageId = imageId;
        this.uniqueID = uniqueID;
        this.role = role;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEducationGroup() {
        return educationGroup;
    }

    public void setEducationGroup(String educationGroup) {
        this.educationGroup = educationGroup;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", educationGroup='" + educationGroup + '\'' +
                ", token='" + token + '\'' +
                ", imageId='" + imageId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public static User fromJson(String json) {
        TypedMessageResponse<User> t = JsonUtils.getResponse(json, User.class);
        return t == null ? null : t.getMessage();
    }

    public User() {
    }
}
