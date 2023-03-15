package edu.mirea.ardyc.umirea.data.repository.impl.timetable;

import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.repository.Repository;

public abstract class TimetableRepository extends Repository<Timetable> {
    @Override
    public abstract Timetable getData();
}
