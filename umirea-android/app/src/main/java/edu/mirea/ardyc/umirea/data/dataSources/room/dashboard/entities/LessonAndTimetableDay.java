package edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;


public class LessonAndTimetableDay {
    @Embedded
    public TimetableDayEntity timetableDay;
    @Relation(
            parentColumn = "id",
            entityColumn = "timetable_day_id"
    )
    public List<LessonEntity> lessonEntities;

    public static LessonAndTimetableDay fromTimetableDay(TimetableDay day) {
        LessonAndTimetableDay lessonAndTimetableDay = new LessonAndTimetableDay();
        lessonAndTimetableDay.timetableDay = new TimetableDayEntity(day.getDay(), day.getMonth(), day.getYear());
        lessonAndTimetableDay.lessonEntities = day.getLessons().stream().map(LessonEntity::fromLesson).collect(Collectors.toList());
        return lessonAndTimetableDay;
    }

    public TimetableDay toTimetableDay() {
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (LessonEntity lesson : lessonEntities) {
            lessons.add(lesson.toLesson());
        }
        TimetableDay day = new TimetableDay(timetableDay.getDay(), timetableDay.getMonth(), timetableDay.getYear(), lessons);
        return day;
    }

    @Override
    public String toString() {
        return "LessonAndTimetableDay{" +
                "timetableDay=" + timetableDay +
                ", lessonEntities=" + lessonEntities +
                '}';
    }
}
