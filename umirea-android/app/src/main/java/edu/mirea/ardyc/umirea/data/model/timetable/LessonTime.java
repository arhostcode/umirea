package edu.mirea.ardyc.umirea.data.model.timetable;

public class LessonTime {

    private static final String[] timeStart = {"9.00", "10.40", "12.40", "14.20", "16.20", "18.00"};
    private static final String[] timeEnd = {"10.30", "12.10", "14.10", "15.50", "17.50", "19.30"};

    public static String getLessonStartTime(int lesson) {
        return timeStart[lesson];
    }

    public static String getLessonEndTime(int lesson) {
        return timeEnd[lesson];
    }

}
