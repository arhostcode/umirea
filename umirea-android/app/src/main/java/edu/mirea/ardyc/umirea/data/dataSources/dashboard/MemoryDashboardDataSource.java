package edu.mirea.ardyc.umirea.data.dataSources.dashboard;

import android.content.Context;

import edu.mirea.ardyc.umirea.data.model.timetable.HomeWork;
import edu.mirea.ardyc.umirea.data.model.timetable.lessons.LabLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.lessons.SeminarLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Task;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableMonth;

public class MemoryDashboardDataSource extends DashboardDataSource {
    public MemoryDashboardDataSource(Context context) {
        super(context);
    }

    public Timetable loadData() {
        return createMockData();
    }

    private Timetable createMockData() {
        HomeWork homeWork = new HomeWork("Выучить наизусть все правила английского языка");
        Task task = new Task("Я не хочу ничего учить");

        Lesson rus = new Lesson.Builder(new LabLesson())
                .withName("Русский язык")
                .withRoom("302A")
                .withTeacher("Красман О.Г.")
                .build();

        Lesson ang = new Lesson.Builder(new SeminarLesson())
                .withName("Английский язык")
                .withRoom("302A")
                .withHomework(homeWork)
                .withTask(task)
                .withTeacher("Красман О.Г.")
                .withLessonTime(2)
                .build();

        Lesson math = new Lesson.Builder(new SeminarLesson())
                .withName("Математический анализ")
                .withRoom("302A")
                .withTeacher("Красман О.Г.")
                .withLessonTime(3)
                .build();

        Lesson lin = new Lesson.Builder(new SeminarLesson())
                .withName("Линейная алгебра")
                .withRoom("302A")
                .withTeacher("Красман О.Г.")
                .withLessonTime(1)
                .build();

        Lesson l = new Lesson.Builder(new SeminarLesson())
                .withName("Линейная алгебра")
                .withRoom("302A")
                .withTeacher("Красман О.Г.")
                .withLessonTime(4)
                .build();

        Lesson l1 = new Lesson.Builder(new SeminarLesson())
                .withName("Линейная алгебра")
                .withRoom("302A")
                .withTeacher("Красман О.Г.")
                .withLessonTime(5)
                .build();

        TimetableDay day = new TimetableDay.Builder()
                .withDate(4, 4, 2023)
                .addLesson(rus).addLesson(ang)
                .build();

        TimetableDay day2 = new TimetableDay.Builder()
                .withDate(5, 4, 2023)
                .addLesson(rus).addLesson(ang)
                .build();

        TimetableDay day3 = new TimetableDay.Builder()
                .withDate(6, 2, 2023)
                .addLesson(rus).addLesson(ang)
                .build();
        TimetableDay day4 = new TimetableDay.Builder()
                .withDate(6, 4, 2023)
                .addLesson(rus).addLesson(ang).addLesson(math)
                .build();
        TimetableDay day5 = new TimetableDay.Builder()
                .withDate(7, 4, 2023)
                .addLesson(rus).addLesson(ang).addLesson(math).addLesson(lin)
                .build();

        TimetableDay day6 = new TimetableDay.Builder()
                .withDate(8, 4, 2023)
                .addLesson(rus).addLesson(ang).addLesson(math).addLesson(lin).addLesson(l)
                .build();

        TimetableDay day7 = new TimetableDay.Builder()
                .withDate(9, 4, 2023)
                .addLesson(rus).addLesson(ang).addLesson(math).addLesson(lin).addLesson(l).addLesson(l1)
                .build();

        TimetableMonth.Builder marchBuilder = new TimetableMonth.Builder(4, 2023).addDay(day).addDay(day2).addDay(day4).addDay(day5).addDay(day6).addDay(day7);
        for (int i = 11; i < 28; i++) {
            marchBuilder.addDay(new TimetableDay.Builder()
                    .withDate(i, 4, 2023)
                    .addLesson(rus).addLesson(ang).addLesson(math).addLesson(lin).addLesson(l).addLesson(l1)
                    .build());
        }
        TimetableMonth february = new TimetableMonth.Builder(2, 2023).addDay(day3).build();
        TimetableMonth march = marchBuilder.build();

        return new Timetable.Builder().addMonth(february).addMonth(march).build();
    }

}
