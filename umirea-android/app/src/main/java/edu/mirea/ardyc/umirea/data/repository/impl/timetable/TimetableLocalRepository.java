package edu.mirea.ardyc.umirea.data.repository.impl.timetable;

import java.util.ArrayList;

import edu.mirea.ardyc.umirea.data.model.timetable.LabLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.LectionLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.SeminarLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;


public class TimetableLocalRepository extends TimetableRepository {
    @Override
    public Timetable getData() {
        Lesson rus = new Lesson.Builder(new LabLesson())
                .withName("Русский язык")
                .withRoom("302A")
                .withTeacher("Красман О.Г.")
                .build();

        Lesson ang = new Lesson.Builder(new SeminarLesson())
                .withName("Английский язык")
                .withRoom("302A")
                .withTeacher("Красман О.Г.")
                .withLessonTime(2)
                .build();
        TimetableDay day = new TimetableDay.Builder()
                .withDate(4, 3, 2023)
                .addLesson(rus).addLesson(ang)
                .build();

        TimetableDay day2 = new TimetableDay.Builder()
                .withDate(5, 3, 2023)
                .addLesson(rus).addLesson(ang)
                .build();

        TimetableDay day3 = new TimetableDay.Builder()
                .withDate(6, 2, 2023)
                .addLesson(rus).addLesson(ang)
                .build();

        return new Timetable.Builder().addDay(day).addDay(day2).addDay(day3).build();
    }
}
