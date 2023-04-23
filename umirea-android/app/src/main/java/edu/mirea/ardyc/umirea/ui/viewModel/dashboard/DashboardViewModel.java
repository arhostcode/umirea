package edu.mirea.ardyc.umirea.ui.viewModel.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.data.DateLesson;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableLocalRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableRemoteRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableRepository;
import edu.mirea.ardyc.umirea.ui.viewModel.UmireaApplication;

public class DashboardViewModel extends AndroidViewModel {

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
        System.out.println(lessons);
        dashboardProcessor.addLessons(lessons);
    }
}