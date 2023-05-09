package edu.mirea.ardyc.umirea.data.dataSources.dashboard;

import android.content.Context;

import java.util.List;
import java.util.stream.Collectors;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.dataSources.room.UmireaDatabase;
import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities.LessonAndTimetableDay;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateTask;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;

public class DashboardDataSource extends DataSource {
    public DashboardDataSource(Context context) {
        super(context);
    }

    public DataResponse<Timetable> getData() {
        List<TimetableDay> dayList = UmireaDatabase.getDatabase(context).timetableDao().getTimetableDays().stream().map(LessonAndTimetableDay::toTimetableDay).collect(Collectors.toList());
        return new DataResponse<>(Timetable.fromDaysList(dayList));
    }

    public void saveData(Timetable timetable) {
        UmireaDatabase.getDatabase(context).timetableDao().deleteAll();
        List<LessonAndTimetableDay> lessonAndTimetableDays = timetable.toDaysList().stream().map(LessonAndTimetableDay::fromTimetableDay).collect(Collectors.toList());
        lessonAndTimetableDays.forEach(UmireaDatabase.getDatabase(context).timetableDao()::insert);
    }

    public void saveAddonLessons(List<DateLesson> dateLessons) {
        dateLessons.forEach(this::saveAddonLesson);
    }

    public void saveAddonLesson(DateLesson dateLesson) {
        UmireaDatabase.getDatabase(context).timetableDao().updateLesson(dateLesson);
    }

    public void saveHomeworks(List<DateTask> dateTasks) {
        dateTasks.forEach(this::saveHomework);
    }

    public void saveHomework(DateTask dateTask) {
        UmireaDatabase.getDatabase(context).timetableDao().updateHomework(dateTask);
    }

    public void saveTasks(List<DateTask> dateTasks) {
        dateTasks.forEach(this::saveTask);
    }

    public void saveTask(DateTask dateTask) {
        UmireaDatabase.getDatabase(context).timetableDao().updateTask(dateTask);
    }

    public void removeData() {
        context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).edit().remove("dashboard_hash").apply();
        UmireaDatabase.getDatabase(context).timetableDao().clearAll();
    }

    public int getDashboardHash() {
        return context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getInt("dashboard_hash", 0);
    }

    public void saveDashboardHash(int hash) {
        context.getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).edit().putInt("dashboard_hash", hash).apply();
    }

}
