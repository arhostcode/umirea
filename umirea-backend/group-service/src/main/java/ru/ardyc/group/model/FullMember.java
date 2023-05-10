package ru.ardyc.group.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class FullMember {

    private String uuid;
    private String firstName;
    private String lastName;
    private String role;
    private String imageId;

    public FullMember(String uuid, String firstName, String lastName, String role, String imageId) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.role = role;
        this.lastName = lastName;
        this.imageId = imageId;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public String getImageId() {
        return imageId;
    }
}
