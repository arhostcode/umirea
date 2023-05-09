package ru.ardyc.schedule.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.json.JsonArray;
import java.util.ArrayList;
import java.util.List;
@RegisterForReflection
public class Groups {

    private List<Group> groups = new ArrayList<>();

    public List<Group> getGroups() {
        return groups;
    }

    public static Groups fromJson(JsonArray json) {
        Groups groups = new Groups();
        for (int i = 0; i < json.size(); i++) {
            groups.getGroups().add(Group.fromJson(json.get(i).asJsonObject()));
        }
        return groups;
    }

}
