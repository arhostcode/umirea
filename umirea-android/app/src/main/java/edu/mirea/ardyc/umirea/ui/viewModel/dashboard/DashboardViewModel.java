package edu.mirea.ardyc.umirea.ui.viewModel.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateTask;
import edu.mirea.ardyc.umirea.ui.viewModel.UmireaApplication;

public class DashboardViewModel extends AndroidViewModel {

    private MutableLiveData<Timetable> timetableMutableLiveData;
    private DashboardProcessor dashboardProcessor;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        dashboardProcessor = ((UmireaApplication) application).getDashboardProcessor();
    }

    public void addLesson(String name, String teacherName, String room, int lessonType, List<Integer> times, List<String> dates) {
        List<DateLesson> lessons = new ArrayList<>();
        Lesson.Builder builder = new Lesson.Builder(lessonType).withName(name).withTeacher(teacherName).withRoom(room);
        for (int i = 0; i < dates.size(); i++) {
            String[] parsedDate = dates.get(i).split("\\.");
            int day = Integer.parseInt(parsedDate[0]);
            int month = Integer.parseInt(parsedDate[1]);
            int year = Integer.parseInt(parsedDate[2]);
            for (int j = 0; j < times.size(); j++) {
                lessons.add(new DateLesson(day, month, year, builder.cloned().withLessonTime(times.get(j)).build()));
            }
        }
        dashboardProcessor.addLessons(lessons, timetableMutableLiveData);
    }

    public void setTimetableMutableLiveData(MutableLiveData<Timetable> timetableMutableLiveData) {
        this.timetableMutableLiveData = timetableMutableLiveData;
    }

    public void updateHomework(DateTask dateTask){
        dashboardProcessor.updateHomework(dateTask, timetableMutableLiveData);
    }

    public void updateTask(DateTask dateTask){
        dashboardProcessor.updateTask(dateTask, timetableMutableLiveData);
    }
}