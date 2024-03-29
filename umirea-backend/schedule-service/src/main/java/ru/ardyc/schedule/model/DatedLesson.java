package ru.ardyc.schedule.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import ru.ardyc.schedule.entities.DatedLessonEntity;
@RegisterForReflection
public class DatedLesson {

    private String uuid;
    private String userUuid;
    private Integer day;
    private Integer month;
    private Integer year;

    private Integer lessonTime;
    private Integer lessonType;

    private String name;

    private String teacherName;

    private String room;

    public DatedLesson(String uuid, String userUuid, Integer day, Integer month, Integer year, Integer lessonTime, Integer lessonType, String name, String teacherName, String room) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.day = day;
        this.month = month;
        this.year = year;
        this.lessonTime = lessonTime;
        this.lessonType = lessonType;
        this.name = name;
        this.teacherName = teacherName;
        this.room = room;
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

    public Integer getLessonType() {
        return lessonType;
    }

    public String getName() {
        return name;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getRoom() {
        return room;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
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

    public void setLessonType(Integer lessonType) {
        this.lessonType = lessonType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public static DatedLesson fromEntity(DatedLessonEntity entity) {
        return new DatedLesson(entity.getUuid(), entity.getUserUuid(), entity.getDay(), entity.getMonth(), entity.getYear(), entity.getLessonTime(), entity.getLessonType(), entity.getName(), entity.getTeacherName(), entity.getRoom());
    }

}
