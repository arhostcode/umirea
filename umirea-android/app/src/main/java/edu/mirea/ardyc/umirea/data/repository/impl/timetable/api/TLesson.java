package edu.mirea.ardyc.umirea.data.repository.impl.timetable.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class TLesson {
    private String name;
    private String type;
    private String teacherName;
    private String room;

    private TLesson() {
    }

    public TLesson(String name, String type, String teacherName, String room) {
        this.name = name;
        this.type = type;
        this.teacherName = teacherName;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "TLesson{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
