package ru.ardyc.schedule.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import ru.ardyc.schedule.entities.NoteEntity;
@RegisterForReflection
public class Note {

    private String uuid;
    private String userUuid;
    private Integer day;
    private Integer month;
    private Integer year;

    private Integer lessonTime;

    private String text;

    public Note(String uuid, String userUuid, Integer day, Integer month, Integer year, Integer lessonTime, String text) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.day = day;
        this.month = month;
        this.year = year;
        this.lessonTime = lessonTime;
        this.text = text;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getLessonTime() {
        return lessonTime;
    }

    public String getText() {
        return text;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public static Note fromEntity(NoteEntity entity) {
        return new Note(entity.getUuid(), entity.getUserUuid(), entity.getDay(), entity.getMonth(), entity.getYear(), entity.getLessonTime(), entity.getText());
    }

}
