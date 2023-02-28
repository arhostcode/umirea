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

    public GroupEntity() {
    }

    public GroupEntity(String uuid, String timeTable) {
        this.uuid = uuid;
        this.timeTable = timeTable;
    }
}
