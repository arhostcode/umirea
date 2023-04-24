package edu.mirea.ardyc.umirea.ui.view.dashboard.adapters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateTask;

public class FullLessonItems extends LessonItems {

    private final int maxLessonsCount = 6;

    public FullLessonItems(List<Lesson> lessons, Consumer<DateTask> homeworkConsumer, Consumer<DateTask> taskConsumer) {
        super(lessons, homeworkConsumer, taskConsumer);
        List<Lesson> l = getLessons();
        updateLessons(l);
    }

    @Override
    public void update(TimetableDay day) {
        super.update(day);
        List<Lesson> lessons = new ArrayList<>(day.getLessons());
        lessons.sort(Comparator.comparingInt(Lesson::getLessonTime));
        updateLessons(lessons);
        super.update(lessons);
    }

    private void updateLessons(List<Lesson> lessons) {
        if (lessons.size() != 0) {
            int lesson = 0;
            while (lesson < maxLessonsCount) {
                if (lesson >= lessons.size() || lessons.get(lesson).getLessonTime() != lesson) {
                    lessons.add(lesson, new Lesson.Builder().withName("Нет пары").withLessonTime(lesson).build());
                }
                lesson++;
            }
        }
    }
}
