package edu.mirea.ardyc.umirea.data.model.timetable;

import java.util.ArrayList;

public class TimetableDay {

    private int day;
    private int month;
    private int year;

    private ArrayList<Lesson> lessons = new ArrayList<>();

    public TimetableDay(int day, int month, int year, ArrayList<Lesson> lessons) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.lessons = lessons;
    }

    private TimetableDay() {
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

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }


    public static class Builder {
        private TimetableDay timetableDay = new TimetableDay();

        public Builder withDate(int day, int month, int year) {
            timetableDay.setDay(day);
            timetableDay.setMonth(month);
            timetableDay.setYear(year);
            return this;
        }

        public Builder withLessons(ArrayList<Lesson> lessons) {
            timetableDay.lessons = lessons;
            return this;
        }

        public Builder addLesson(Lesson lesson) {
            timetableDay.lessons.add(lesson);
            return this;
        }

        public TimetableDay build(){
            return timetableDay;
        }
    }

}
