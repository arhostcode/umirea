package edu.mirea.ardyc.umirea.data.repository.impl.timetable.api;

import java.util.ArrayList;

public class TWeek {
    private ArrayList<TDay> days = new ArrayList<>(7);

    public TWeek() {
        for (int i = 0; i < 7; i++) {
            days.add(new TDay());
        }
    }

    public ArrayList<TDay> getDays() {
        return days;
    }

    @Override
    public String toString() {
        return "TWeek{" +
                "days=" + days +
                '}';
    }
}
