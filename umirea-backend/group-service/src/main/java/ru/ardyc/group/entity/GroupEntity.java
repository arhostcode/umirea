package ru.ardyc.group.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;

    private String name;

    private String token;

    private String timeTable;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(String name) {
        this.timeTable = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GroupEntity() {
    }

    public GroupEntity(String uuid, String name, String timeTable, String token) {
        this.uuid = uuid;
        this.name = name;
        this.token = token;
        this.timeTable = timeTable;
    }
}
