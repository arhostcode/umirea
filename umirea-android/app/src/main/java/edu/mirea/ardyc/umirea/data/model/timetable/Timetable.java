package edu.mirea.ardyc.umirea.data.model.timetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Timetable {

    private ArrayList<TimetableMonth> timetableMonths = new ArrayList<>();

    public Timetable(ArrayList<TimetableMonth> timetableMonths) {
        this.timetableMonths = timetableMonths;
    }

    private Timetable() {
    }

    public List<TimetableDay> toDaysList() {
        List<TimetableDay> dayList = new ArrayList<>();
        for (TimetableMonth month : timetableMonths) {
            for (TimetableDay day : month.getDays().values()) {
                dayList.add(day);
            }
        }
        return dayList;
    }

    public static Timetable fromDaysList(List<TimetableDay> days) {
        Timetable timetable = new Timetable();
        for (TimetableDay day : days) {
            TimetableMonth month = timetable.getForMonthYear(day.getMonth(), day.getYear());
            if (month != null) {
                month.putDay(day);
            } else {
                month = new TimetableMonth(day.getMonth(), day.getYear());
                month.putDay(day);
                timetable.timetableMonths.add(month);
            }
        }
        return timetable;
    }

    public ArrayList<TimetableMonth> getTimetableMonths() {
        return timetableMonths;
    }

    public static class Builder {
        private Timetable timetable = new Timetable();

        public Builder addMonth(TimetableMonth month) {
            timetable.timetableMonths.add(month);
            return this;
        }

        public Timetable build() {
            return timetable;
        }
    }

    public TimetableMonth getForMonthYear(int monthId, int year) {
        return timetableMonths.stream().filter((m) -> m.getId() == monthId && m.getYear() == year).findFirst().orElse(null);
    }

}
