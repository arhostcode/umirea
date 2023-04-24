package edu.mirea.ardyc.umirea.data.repository.impl.timetable;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.dashboard.MemoryDashboardDataSource;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateLesson;

public class TimetableMemoryRepository extends TimetableRepository {
    public TimetableMemoryRepository(Context context) {
        super(context);
    }

    @Override
    public Timetable getData() {
        return new MemoryDashboardDataSource(context).loadData();
    }

    @Override
    public List<String> listGroupsSchedules() {
        ArrayList<String> list = new ArrayList<>();
        list.add("ИКБО-01-22");
        list.add("ИКБО-02-22");
        list.add("ИКБО-03-22");
        list.add("ИКБО-04-22");
        list.add("ИКБО-05-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        list.add("ИКБО-06-22");
        return list;
    }


    @Override
    public String getBaseScheduleHash() {
        return "asdasdfllflflfmnggckd";
    }

    @Override
    public String getAddonLessonsHash() {
        return "asdasdfllflflfmnggcdkd";
    }

    @Override
    public List<DateLesson> getAddonLessons() {
        ArrayList<DateLesson> newLessons = new ArrayList<>();
        newLessons.add(new DateLesson(1, 4, 2023, new Lesson.Builder(1).withName("Хуесос").build()));
        newLessons.add(new DateLesson(5, 4, 2023, new Lesson.Builder(1).withName("Пидор").build()));
        newLessons.add(new DateLesson(10, 4, 2023, new Lesson.Builder(1).withName("Залупа").build()));
        return newLessons;
    }

}
