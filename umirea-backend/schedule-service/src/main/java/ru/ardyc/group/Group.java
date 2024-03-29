package ru.ardyc.group;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.quarkus.runtime.annotations.RegisterForReflection;
import ru.ardyc.response.TypedMessageResponse;
import ru.ardyc.utils.JsonUtils;

@RegisterForReflection
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Group {

    private String uuid;

    private String timeTable;

    private String name;

    private String token;


    public Group(String uuid, String timeTable, String name, String token) {
        this.uuid = uuid;
        this.timeTable = timeTable;
        this.name = name;
        this.token = token;
    }

    public Group() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Group fromJson(String json) {
        TypedMessageResponse<Group> t = JsonUtils.getResponse(json, Group.class);
        return t == null ? null : t.getMessage();
    }

}
