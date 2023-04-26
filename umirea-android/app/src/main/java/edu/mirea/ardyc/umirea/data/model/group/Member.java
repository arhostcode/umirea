package edu.mirea.ardyc.umirea.data.model.group;

import java.util.UUID;

public class Member {

    private String firstName;

    private String lastName;

    private String role;

    private String imagePath;

    private String uuid;

    public Member(String firstName, String lastName, String role, String uuid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.uuid = uuid;
    }

    public Member(String firstName, String lastName, String role, String uuid, String imagePath) {
        this(firstName, lastName, role, uuid);
        this.imagePath = imagePath;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getRole() {
        return role;
    }

    public String getImagePath() {
        return imagePath;
    }
}
