package edu.mirea.ardyc.umirea.data.repository.impl.timetable;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.data.DateLesson;
import edu.mirea.ardyc.umirea.data.repository.Repository;

public abstract class TimetableRepository extends Repository<Timetable> {
    public TimetableRepository(Context context) {
        super(context);
    }

    public abstract List<String> listGroupsSchedules();

    public abstract String getBaseScheduleHash();

    public abstract String getAddonLessonsHash();

    public abstract List<DateLesson> getAddonLessons();

}
