package edu.mirea.ardyc.umirea.ui.viewModel.dashboard;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateTask;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.DashboardRepository;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;
import edu.mirea.ardyc.umirea.ui.viewModel.AppViewModel;
import edu.mirea.ardyc.umirea.ui.viewModel.UmireaApplication;

@HiltViewModel
public class DashboardViewModel extends AndroidViewModel {

    private MutableLiveData<Timetable> timetableMutableLiveData = new MutableLiveData<>();
    private DashboardRepository dashboardRepository;

    @Inject
    public DashboardViewModel(@NonNull Application application, DashboardRepository dashboardRepository) {
        super(application);
        this.dashboardRepository = dashboardRepository;
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
        AppViewModel.executorService.execute(() -> {
            String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
            dashboardRepository.addLessons(userToken, lessons);
            dashboardRepository.loadAddonLessons(userToken);
            timetableMutableLiveData.postValue(dashboardRepository.getLocalDashboard().getData());
        });

    }

    public void setTimetableMutableLiveData(MutableLiveData<Timetable> timetableMutableLiveData) {
        this.timetableMutableLiveData = timetableMutableLiveData;
    }

    public void updateHomework(DateTask dateTask) {
        AppViewModel.executorService.execute(() -> {
            String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
            dashboardRepository.addHomework(userToken, dateTask);
            dashboardRepository.loadHomeworks(userToken);
            timetableMutableLiveData.postValue(dashboardRepository.getLocalDashboard().getData());
        });
    }

    public void updateTask(DateTask dateTask) {
        AppViewModel.executorService.execute(() -> {
            String userToken = getApplication().getSharedPreferences(AppActivity.APP_PATH, Context.MODE_PRIVATE).getString("user_token", "");
            dashboardRepository.addNote(userToken, dateTask);
            dashboardRepository.loadNotes(userToken);
            timetableMutableLiveData.postValue(dashboardRepository.getLocalDashboard().getData());
        });
    }
}