package edu.mirea.ardyc.umirea.data.model.timetable.data;

import edu.mirea.ardyc.umirea.data.model.timetable.HomeWork;

public class DateHomework {

    private int day;
    private int month;
    private int year;
    private int lesson;

    private HomeWork homeWork;

    public DateHomework(int day, int month, int year, int lesson, HomeWork homeWork) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.lesson = lesson;
        this.homeWork = homeWork;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getLesson() {
        return lesson;
    }

    public HomeWork getHomeWork() {
        return homeWork;
    }
}
