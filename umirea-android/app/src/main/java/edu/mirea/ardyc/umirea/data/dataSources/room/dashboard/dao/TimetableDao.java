package edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities.LessonAndTimetableDay;
import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities.LessonEntity;
import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities.TimetableDayEntity;
import edu.mirea.ardyc.umirea.data.model.timetable.data.DateLesson;

@Dao
public abstract class TimetableDao {

    @Transaction
    @Query("SELECT * FROM timetable_days")
    public abstract List<LessonAndTimetableDay> getTimetableDays();

    @Transaction
    @Update
    public abstract void update(LessonEntity entity);


    @Transaction
    @Query("SELECT * FROM timetable_days WHERE day=:day and month=:month and year=:year")
    public abstract LessonAndTimetableDay getByDay(int day, int month, int year);

    @Transaction
    public void insert(LessonAndTimetableDay lessonAndTimetableDay) {
        long id = insert(lessonAndTimetableDay.timetableDay);
        for (LessonEntity lesson : lessonAndTimetableDay.lessonEntities) {
            lesson.timetableDayId = (int) id;
            insert(lesson);
        }
    }


    @Transaction
    public void updateLesson(DateLesson dateLesson) {
        LessonAndTimetableDay day = getByDay(dateLesson.getDay(), dateLesson.getMonth(), dateLesson.getYear());
        if (day == null) {
            LessonAndTimetableDay ld = new LessonAndTimetableDay();
            ld.timetableDay = new TimetableDayEntity(dateLesson.getDay(), dateLesson.getMonth(), dateLesson.getYear());
            ld.lessonEntities = new ArrayList<>();
            ld.lessonEntities.add(LessonEntity.fromLesson(dateLesson.getLesson()));
            insert(ld);
            return;
        }
        for (int i = 0; i < day.lessonEntities.size(); i++) {
            if (day.lessonEntities.get(i).getLessonTime() == dateLesson.getLesson().getLessonTime()) {
                if (dateLesson.getLesson().equals(day.lessonEntities.get(i)))
                    return;
                day.lessonEntities.get(i).setHomeWork(dateLesson.getLesson().getHomeWork());
                day.lessonEntities.get(i).setTask(dateLesson.getLesson().getTask());
                day.lessonEntities.get(i).setRoom(dateLesson.getLesson().getRoom());
                day.lessonEntities.get(i).setName(dateLesson.getLesson().getName());
                day.lessonEntities.get(i).setTeacherName(dateLesson.getLesson().getTeacherName());
                day.lessonEntities.get(i).setLessonType(dateLesson.getLesson().getType());
                update(day.lessonEntities.get(i));
                return;
            }
        }
        LessonEntity l = LessonEntity.fromLesson(dateLesson.getLesson());
        l.timetableDayId = day.timetableDay.getId();
        insert(l);
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(TimetableDayEntity timetableDay);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(LessonEntity lesson);

    @Transaction
    public void deleteAll() {
        deleteAllDays();
        deleteAllLessons();
    }

    @Query("DELETE FROM timetable_days")
    public abstract void deleteAllDays();

    @Query("DELETE FROM timetable_lessons")
    public abstract void deleteAllLessons();


}
