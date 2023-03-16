package edu.mirea.ardyc.umirea.data.model.auth;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("login")
    private String login;
    @SerializedName("educationGroup")
    private String educationGroup;
    @SerializedName("token")
    private String token;
    @SerializedName("uniqueID")
    private String uniqueID;
    @SerializedName("role")
    private String role;

    public User(String firstName, String lastName, String login, String educationGroup, String token, String uniqueID, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.educationGroup = educationGroup;
        this.token = token;
        this.uniqueID = uniqueID;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", educationGroup='" + educationGroup + '\'' +
                ", token='" + token + '\'' +
                ", uniqueID='" + uniqueID + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}