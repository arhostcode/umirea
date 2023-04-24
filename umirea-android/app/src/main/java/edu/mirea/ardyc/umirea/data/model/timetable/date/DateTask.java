package edu.mirea.ardyc.umirea.data.model.timetable.date;

import edu.mirea.ardyc.umirea.data.model.timetable.HomeWork;
import edu.mirea.ardyc.umirea.data.model.timetable.Task;

public class DateTask {

    private int day;
    private int month;
    private int year;
    private int lesson;

    private Task task;

    public DateTask(int day, int month, int year, int lesson, Task homeWork) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.lesson = lesson;
        this.task = homeWork;
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

    public Task getTask() {
        return task;
    }


}
