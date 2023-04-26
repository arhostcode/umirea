package edu.mirea.ardyc.umirea.data.model.timetable.date;

import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;

public class DateLesson {

    private int day;
    private int month;
    private int year;

    private Lesson lesson;

    public DateLesson(int day, int month, int year, Lesson lesson) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.lesson = lesson;
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

    public Lesson getLesson() {
        return lesson;
    }

    @Override
    public String toString() {
        return "DateLesson{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", lesson=" + lesson +
                '}';
    }
}
