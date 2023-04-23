package edu.mirea.ardyc.umirea.ui.view.dashboard.adapters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;

public class FullLessonItems extends LessonItems {

    private final int maxLessonsCount = 6;

    public FullLessonItems(List<Lesson> l) {
        super(l);

        List<Lesson> lessons = getLessons();

        if (lessons.size() != 0) {
            int i = 0;
            while (i < maxLessonsCount) {
                if (i >= lessons.size() || lessons.get(i).getLessonTime() != i) {
                    lessons.add(i, new Lesson.Builder().withName("Нет пары").withLessonTime(i).build());
                }
                i++;
            }
        }
    }

    @Override
    public void update(List<Lesson> lessonsList) {
        List<Lesson> lessons = new ArrayList<>(lessonsList);
        lessons.sort(Comparator.comparingInt(Lesson::getLessonTime));

        if (lessons.size() != 0) {
            int lesson = 0;
            while (lesson < maxLessonsCount) {
                if (lesson >= lessons.size() || lessons.get(lesson).getLessonTime() != lesson) {
                    lessons.add(lesson, new Lesson.Builder().withName("Нет пары").withLessonTime(lesson).build());
                }
                lesson++;
            }
        }
        super.update(lessons);
    }
}
