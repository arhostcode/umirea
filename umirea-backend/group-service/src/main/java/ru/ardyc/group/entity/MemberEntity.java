package ru.ardyc.group.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String role;

    private String groupId;

    private String imageId;

    public MemberEntity() {

    }

    public MemberEntity(String uuid, String role, String groupId, String imageId) {
        this.uuid = uuid;
        this.role = role;
        this.groupId = groupId;
        this.imageId = imageId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String name) {
        this.uuid = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String group) {
        this.groupId = group;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "MemberEntity{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", role='" + role + '\'' +
                ", groupId='" + groupId + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }
}
