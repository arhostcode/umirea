package edu.mirea.ardyc.umirea.data.model.group;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private List<Member> members = new ArrayList<>();
    private String name;
    private String token;
    private String baseSchedule;

    public Group(List<Member> members, String name, String baseSchedule) {
        this.members = members;
        this.name = name;
        this.baseSchedule = baseSchedule;
    }

    public Group(List<Member> members, String name, String token, String baseSchedule) {
        this.members = members;
        this.name = name;
        this.token = token;
        this.baseSchedule = baseSchedule;
    }

    public Group() {
    }

    public List<Member> getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }

    public String getBaseSchedule() {
        return baseSchedule;
    }

    public String getToken() {
        return token;
    }

    public void setBaseSchedule(String baseSchedule) {
        this.baseSchedule = baseSchedule;
    }

    public Member getById(String id) {
        for (Member m : members) {
            if (m.getUuid().equals(id))
                return m;
        }
        return null;
    }
}
