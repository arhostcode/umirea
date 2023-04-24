package edu.mirea.ardyc.umirea.ui.viewModel.dashboard;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.timetable.HomeWork;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableMonth;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateTask;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableLocalRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableMemoryRepository;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.TimetableRepository;

public class DashboardProcessor {

    public static DashboardProcessor create(Application application) {
        return new DashboardProcessor(application);
    }

    private final Application application;

    private DashboardProcessor(Application application) {
        this.application = application;
    }

    private TimetableLocalRepository timetableLocalRepository;
    private TimetableRepository timetableRepository;

    public MutableLiveData<Timetable> initTimetableData() {
        MutableLiveData<Timetable> mutableLiveData = new MutableLiveData<>();
        new Thread(() -> {
            String hash = application.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).getString("timetable_hash", "");
            timetableRepository = new TimetableMemoryRepository(application);
            timetableLocalRepository = new TimetableLocalRepository(application);
            String remoteHash = timetableRepository.getBaseScheduleHash();

            if (!hash.equals(remoteHash)) {
                Timetable data = timetableRepository.getData();
                timetableLocalRepository.updateData(timetableRepository.getData());
                application.getSharedPreferences("edu.mirea.ardyc.umirea", Context.MODE_PRIVATE).edit().putString("timetable_hash", remoteHash).apply();
                timetableProcessing(data, mutableLiveData);
            } else {
                timetableLocalRepository.getDataAndPerform((val) -> {
                    timetableProcessing(val, mutableLiveData);
                });
            }
        }).start();
        return mutableLiveData;
    }

    private void timetableProcessing(Timetable timetable, MutableLiveData<Timetable> timetableMutableLiveData) {
        timetableMutableLiveData.postValue(timetable);
//        processAddonLessons(timetable);
        timetableMutableLiveData.postValue(timetable);
    }

    public void addLessons(List<DateLesson> lessons, MutableLiveData<Timetable> timetableMutableLiveData) {
        lessons.forEach(timetableLocalRepository::updateLesson);
        Timetable t = timetableMutableLiveData.getValue();
        lessons.forEach((lesson) -> {
            TimetableMonth month = t.getForMonthYear(lesson.getMonth(), lesson.getYear());
            if (month == null) {
                month = new TimetableMonth(lesson.getMonth(), lesson.getYear());
                month.putDay(new TimetableDay.Builder().withDate(lesson.getDay(), lesson.getMonth(), lesson.getYear()).addLesson(lesson.getLesson()).build());
                t.getTimetableMonths().add(month);
            } else {
                TimetableDay day = month.getDay(lesson.getDay());
                if (day == null) {
                    month.putDay(new TimetableDay.Builder().withDate(lesson.getDay(), lesson.getMonth(), lesson.getYear()).addLesson(lesson.getLesson()).build());
                } else {
                    boolean processed = false;
                    for (int i = 0; i < day.getLessons().size(); i++) {
                        if (day.getLessons().get(i).getLessonTime() == lesson.getLesson().getLessonTime()) {
                            day.getLessons().set(i, lesson.getLesson());
                            processed = true;
                        }
                    }
                    if (!processed)
                        day.getLessons().add(lesson.getLesson());
                }
            }
        });
        timetableMutableLiveData.postValue(t);
//        timetableLocalRepository.getDataAndPerform(timetableMutableLiveData::postValue);
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

    public List<String> getGroupsSchedules() {
        return timetableRepository.listGroupsSchedules();
    }

    public void updateHomework(DateTask task, MutableLiveData<Timetable> data) {
        Timetable timetable = data.getValue();
        if (timetable == null)
            return;
        TimetableMonth month = timetable.getForMonthYear(task.getMonth(), task.getYear());
        if (month == null)
            return;
        TimetableDay day = month.getDay(task.getDay());
        if (day == null)
            return;
        day.getLessons().forEach(lesson -> {
            if (lesson.getLessonTime() == task.getLesson())
                lesson.setHomeWork(HomeWork.fromTask(task.getTask()));
        });
        data.postValue(timetable);
        timetableLocalRepository.updateHomework(task);
    }

    public void updateTask(DateTask task, MutableLiveData<Timetable> data) {
        Timetable timetable = data.getValue();
        if (timetable == null)
            return;
        TimetableMonth month = timetable.getForMonthYear(task.getMonth(), task.getYear());
        if (month == null)
            return;
        TimetableDay day = month.getDay(task.getDay());
        if (day == null)
            return;
        day.getLessons().forEach(lesson -> {
            if (lesson.getLessonTime() == task.getLesson())
                lesson.setTask(task.getTask());
        });
        data.postValue(timetable);
        timetableLocalRepository.updateTask(task);
    }
}
