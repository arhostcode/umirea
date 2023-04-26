package edu.mirea.ardyc.umirea.data.repository.impl.timetable.api;

import java.util.ArrayList;
import java.util.List;

public class TDay {

    private List<TLesson> lessons = new ArrayList<>(10);

    public TDay() {
        for (int i = 0; i < 10; i++) {
            lessons.add(null);
        }
    }

    public List<TLesson> getLessons() {
        return lessons;
    }

    @Override
    public String toString() {
        return "TDay{" +
                "lessons=" + lessons +
                '}';
    }
}
