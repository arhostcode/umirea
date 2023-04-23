package edu.mirea.ardyc.umirea.data.model.group;

import java.util.List;

public class AdminGroup extends Group {

    private String id;
    private String token;

    public AdminGroup(List<Member> members, String name, String baseSchedule, String id, String token) {
        super(members, name, baseSchedule);
        this.id = id;
        this.token = token;
    }
}
