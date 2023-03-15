package edu.mirea.ardyc.umirea.ui.view.dashboard.timetable;

import java.util.ArrayList;

import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;

public class FullLessonItems extends LessonItems {

    public FullLessonItems(ArrayList<Lesson> l) {
        super(l);

        ArrayList<Lesson> lessons = getLessons();

        if (lessons.size() != 0) {
            int i = 0;
            while (i < 6) {
                if (i >= lessons.size() || lessons.get(i).getLessonTime() != i) {
                    lessons.add(i, new Lesson.Builder().withName("Нет пары").withLessonTime(i).build());
                }
                i++;
            }
        }
    }
}
