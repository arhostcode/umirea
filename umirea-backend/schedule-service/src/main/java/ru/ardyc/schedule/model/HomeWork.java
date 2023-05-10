package ru.ardyc.schedule.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import ru.ardyc.schedule.entities.HomeWorkEntity;

@RegisterForReflection
public class HomeWork {

    private String uuid;
    private String groupUuid;
    private Integer day;
    private Integer month;
    private Integer year;

    private Integer lessonTime;

    private String text;

    public HomeWork(String uuid, String groupUuid, Integer day, Integer month, Integer year, Integer lessonTime, String text) {
        this.uuid = uuid;
        this.groupUuid = groupUuid;
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

    public String getGroupUuid() {
        return groupUuid;
    }

    public static HomeWork fromEntity(HomeWorkEntity entity) {
        return new HomeWork(entity.getUuid(), entity.getGroupUuid(), entity.getDay(), entity.getMonth(), entity.getYear(), entity.getLessonTime(), entity.getText());
    }
}
