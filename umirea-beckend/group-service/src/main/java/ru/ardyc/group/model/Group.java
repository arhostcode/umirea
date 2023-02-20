package ru.ardyc.group.model;

import ru.ardyc.group.entity.GroupEntity;

public class Group {

    private String uuid;

    private String timeTable;

    public Group(String uuid, String timeTable) {
        this.uuid = uuid;
        this.timeTable = timeTable;
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

    public void setTimeTable(String timeTable) {
        this.timeTable = timeTable;
    }

    public static Group fromEntity(GroupEntity group) {
        return new Group(group.getUuid(), group.getTimeTable());
    }
}
