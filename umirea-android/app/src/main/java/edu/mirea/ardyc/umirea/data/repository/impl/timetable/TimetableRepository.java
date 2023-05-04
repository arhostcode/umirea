package edu.mirea.ardyc.umirea.data.repository.impl.timetable;

import android.content.Context;

import java.util.List;

import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateLesson;
import edu.mirea.ardyc.umirea.data.repository.Repository;

public abstract class TimetableRepository extends Repository {
    public TimetableRepository(Context context) {
        super(context);
    }

    public abstract List<String> listGroupsSchedules();

    public abstract String getBaseScheduleHash();

    public abstract String getAddonLessonsHash();

    public abstract List<DateLesson> getAddonLessons();

}
