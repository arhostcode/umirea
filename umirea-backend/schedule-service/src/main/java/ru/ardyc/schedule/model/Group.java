package ru.ardyc.schedule.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.json.JsonObject;
@RegisterForReflection
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Group {
    private String name;
    private String suffix;

    public Group(String name, String suffix) {
        this.name = name;
        this.suffix = suffix;
    }

    public String getName() {
        return name;
    }

    public String getSuffix() {
        return suffix;
    }

    public static Group fromJson(JsonObject json) {
        String name = json.getString("groupName");
        String suffix = json.getString("groupSuffix");
        return new Group(name, suffix);
    }


}
