package edu.mirea.ardyc.umirea.data.model.timetable;

import java.util.HashMap;

public class TimetableMonth {

    private HashMap<Integer, TimetableDay> days = new HashMap<>();
    private final int id;
    private final int year;

    public TimetableMonth(int id, int year) {
        this.id = id;
        this.year = year;
    }

    public HashMap<Integer, TimetableDay> getDays() {
        return days;
    }

    public void putDay(TimetableDay day) {
        days.putIfAbsent(day.getDay(), day);
    }

    public TimetableDay getDay(int day) {
        return days.get(day);
    }

    public int getYear() {
        return year;
    }

    public int getId() {
        return id;
    }

    public static class Builder {

        private final TimetableMonth timetableMonth;

        public Builder(int id, int year) {
            this.timetableMonth = new TimetableMonth(id, year);
        }

        public TimetableMonth.Builder addDay(TimetableDay day) {
            timetableMonth.putDay(day);
            return this;
        }

        public TimetableMonth build() {
            return timetableMonth;
        }
    }
}
