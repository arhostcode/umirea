package edu.mirea.ardyc.umirea.ui.viewModel.dashboard;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.group.Group;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableMonth;
import edu.mirea.ardyc.umirea.data.model.timetable.data.DateLesson;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableLocalRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableMemoryRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableRepository;

public class DashboardProcessor {

    private static DashboardProcessor instance;

    public static DashboardProcessor getInstance(Application application) {
        if (instance == null)
            instance = new DashboardProcessor(application);
        return instance;
    }

    private final Application application;

    private DashboardProcessor(Application application) {
        this.application = application;
    }

    private TimetableLocalRepository timetableLocalRepository;
    private TimetableRepository timetableRepository;

    private MutableLiveData<Timetable> timetableMutableLiveData = new MutableLiveData<>();

    public void initTimetableData() {
        new Thread(() -> {
            String hash = application.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).getString("timetable_hash", "");
            timetableRepository = new TimetableMemoryRepository(application);
            timetableLocalRepository = new TimetableLocalRepository(application);
            String remoteHash = timetableRepository.getBaseScheduleHash();

            if (!hash.equals(remoteHash)) {
                Timetable data = timetableRepository.getData();
                timetableLocalRepository.updateData(timetableRepository.getData());
                application.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).edit().putString("timetable_hash", remoteHash).apply();
                timetableProcessing(data);
            } else {
                timetableLocalRepository.getDataAndPerform(this::timetableProcessing);
            }
        }).start();

    }

    private void timetableProcessing(Timetable timetable) {
        timetableMutableLiveData.postValue(timetable);
//        processAddonLessons(timetable);
        timetableMutableLiveData.postValue(timetable);
    }

    public void addLessons(List<DateLesson> lessons) {
        lessons.forEach(timetableLocalRepository::updateLesson);
        timetableLocalRepository.getDataAndPerform((t) -> timetableMutableLiveData.postValue(t));
    }


    //TODO TO STORING IN BACKEND
    public void processAddonLessons(Timetable timetable) {
        List<DateLesson> lessons = timetableRepository.getAddonLessons();
        String hash = application.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).getString("timetable_hash_addon_lessons", "");
        String remoteHash = timetableRepository.getAddonLessonsHash();
        if (!hash.equals(remoteHash)) {
            lessons.forEach(timetableLocalRepository::updateLesson);
            lessons.forEach((lesson) -> {
                TimetableMonth month = timetable.getForMonthYear(lesson.getMonth(), lesson.getYear());
                if (month == null) {
                    month = new TimetableMonth(lesson.getMonth(), lesson.getYear());
                    month.putDay(new TimetableDay.Builder().withDate(lesson.getDay(), lesson.getMonth(), lesson.getYear()).addLesson(lesson.getLesson()).build());
                    timetable.getTimetableMonths().add(month);
                } else {
                    TimetableDay day = month.getDay(lesson.getDay());
                    if (day == null) {
                        month.putDay(new TimetableDay.Builder().withDate(lesson.getDay(), lesson.getMonth(), lesson.getYear()).addLesson(lesson.getLesson()).build());
                    } else {
                        for (int i = 0; i < day.getLessons().size(); i++) {
                            if (day.getLessons().get(i).getLessonTime() == lesson.getLesson().getLessonTime()) {
                                day.getLessons().set(i, lesson.getLesson());
                            }
                        }
                        month.putDay(day);
                    }
                }
                application.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).edit().putString("timetable_hash_addon_lessons", remoteHash).apply();
            });

        }
    }

    public MutableLiveData<Timetable> getTimetableMutableLiveData() {
        return timetableMutableLiveData;
    }

    public List<String> getGroupsSchedules() {
        return timetableRepository.listGroupsSchedules();
    }
}
