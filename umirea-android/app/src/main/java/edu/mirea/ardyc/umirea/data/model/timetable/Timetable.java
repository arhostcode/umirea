package edu.mirea.ardyc.umirea.data.model.timetable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Timetable {

    private ArrayList<TimetableDay> timetableDays = new ArrayList<>();

    public Timetable(ArrayList<TimetableDay> timetableDays) {
        this.timetableDays = timetableDays;
    }

    private Timetable() {
    }

    public ArrayList<TimetableDay> getTimetableDays() {
        return timetableDays;
    }

    public static class Builder {
        private Timetable timetable = new Timetable();

        public Builder addDay(TimetableDay day) {
            timetable.timetableDays.add(day);
            return this;
        }

        public Timetable build() {
            return timetable;
        }
    }

    public List<TimetableDay> getForMonthYear(int month, int year) {
        return timetableDays.stream().filter(timetableDay -> timetableDay.getMonth() == month && timetableDay.getYear() == year).collect(Collectors.toList());
    }

}
