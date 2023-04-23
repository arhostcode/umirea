package edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;

@Entity(tableName = "timetable_days")
public class TimetableDayEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "day")
    private int day;
    @ColumnInfo(name = "month")
    private int month;
    @ColumnInfo(name = "year")
    private int year;

    public TimetableDayEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public TimetableDayEntity(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return "TimetableDayEntity{" +
                "id=" + id +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
