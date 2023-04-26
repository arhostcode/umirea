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

    public static int getLessonByTime(int hour, int minute) {
        int current = -1;
        int startHour = Integer.parseInt(timeStart[0].split("\\.")[0]);
        int startMinute = Integer.parseInt(timeStart[0].split("\\.")[1]);

        int endHour = Integer.parseInt(timeEnd[timeEnd.length - 1].split("\\.")[0]);
        int endMinute = Integer.parseInt(timeEnd[timeEnd.length - 1].split("\\.")[1]);
        if (startHour > hour || (startHour == hour && startMinute > minute))
            return -1;
        if (endHour < hour || (endHour == hour && endMinute < minute))
            return -1;

        for (int i = 0; i < timeStart.length; i++) {
            int h = Integer.parseInt(timeStart[i].split("\\.")[0]);
            int m = Integer.parseInt(timeStart[i].split("\\.")[1]);

            if (h < hour || (h == hour && m < minute)) {
                current = i;
            } else break;
        }
        return current;
    }

}
