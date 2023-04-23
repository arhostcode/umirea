package edu.mirea.ardyc.umirea.data.repository.impl.timetable;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.stream.Collectors;

import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities.LessonAndTimetableDay;
import edu.mirea.ardyc.umirea.data.dataSources.room.UmireaDatabase;
import edu.mirea.ardyc.umirea.data.model.timetable.LectionLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.model.timetable.data.DateLesson;
import edu.mirea.ardyc.umirea.data.repository.LocalRepository;


public class TimetableLocalRepository extends LocalRepository<Timetable> {
    public TimetableLocalRepository(Context context) {
        super(context);
    }

    @Override
    public MutableLiveData<Timetable> getData() {
        MutableLiveData<Timetable> timetableMutableLiveData = new MutableLiveData<>();
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            List<TimetableDay> dayList = UmireaDatabase.getDatabase(context).timetableDao().getTimetableDays().stream().map(LessonAndTimetableDay::toTimetableDay).collect(Collectors.toList());
            timetableMutableLiveData.postValue(Timetable.fromDaysList(dayList));
        });
        return timetableMutableLiveData;
    }

    public void getDataAndPerform(TimetableAction action) {
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            List<TimetableDay> dayList = UmireaDatabase.getDatabase(context).timetableDao().getTimetableDays().stream().map(LessonAndTimetableDay::toTimetableDay).collect(Collectors.toList());
            action.perform(Timetable.fromDaysList(dayList));
        });
    }


    public void updateData(Timetable timetable) {
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            UmireaDatabase.getDatabase(context).timetableDao().deleteAll();
            List<LessonAndTimetableDay> lessonAndTimetableDays = timetable.toDaysList().stream().map(LessonAndTimetableDay::fromTimetableDay).collect(Collectors.toList());
            lessonAndTimetableDays.forEach(UmireaDatabase.getDatabase(context).timetableDao()::insert);
        });
    }

    public void updateLesson(DateLesson dateLesson) {
        UmireaDatabase.databaseWriteExecutor.execute(() -> {
            UmireaDatabase.getDatabase(context).timetableDao().updateLesson(dateLesson);
        });
    }

}
