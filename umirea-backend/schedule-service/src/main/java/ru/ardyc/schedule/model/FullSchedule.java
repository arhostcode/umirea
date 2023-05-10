package ru.ardyc.schedule.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class FullSchedule {

    private int firstDay;
    private int firstMonth;
    private int year;
    private Schedule schedule;

    public FullSchedule(int firstDay, int firstMonth, int year, Schedule schedule) {
        this.firstDay = firstDay;
        this.firstMonth = firstMonth;
        this.year = year;
        this.schedule = schedule;
    }

    public int getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(int firstDay) {
        this.firstDay = firstDay;
    }

    public int getFirstMonth() {
        return firstMonth;
    }

    public void setFirstMonth(int firstMonth) {
        this.firstMonth = firstMonth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
