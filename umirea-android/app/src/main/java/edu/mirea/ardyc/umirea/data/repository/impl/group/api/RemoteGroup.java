package edu.mirea.ardyc.umirea.data.repository.impl.group.api;

public class RemoteGroup {
    private String uuid;

    private String timeTable;

    private String name;

    private String token;

    public RemoteGroup(String uuid, String timeTable, String name, String token) {
        this.uuid = uuid;
        this.timeTable = timeTable;
        this.name = name;
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public String getTimeTable() {
        return timeTable;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
