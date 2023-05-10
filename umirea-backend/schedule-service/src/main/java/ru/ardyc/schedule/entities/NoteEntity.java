package ru.ardyc.schedule.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NoteEntity {
    @Id
    private String uuid;
    private String userUuid;
    private Integer day;
    private Integer month;
    private Integer year;

    private Integer lessonTime;
    private String text;

    public NoteEntity() {

    }

    public void setUuid(String id) {
        this.uuid = id;
    }

    public String getUuid() {
        return uuid;
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

    public void setDay(Integer day) {
        this.day = day;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setLessonTime(Integer lessonTime) {
        this.lessonTime = lessonTime;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public NoteEntity(String uuid, String userUuid, Integer day, Integer month, Integer year, Integer lessonTime, String text) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.day = day;
        this.month = month;
        this.year = year;
        this.lessonTime = lessonTime;
        this.text = text;
    }
}
