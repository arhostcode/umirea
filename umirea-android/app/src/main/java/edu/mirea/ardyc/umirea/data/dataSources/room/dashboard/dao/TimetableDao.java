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
import edu.mirea.ardyc.umirea.data.model.timetable.HomeWork;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateTask;

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

    @Transaction
    public void updateHomework(DateTask homework) {
        LessonAndTimetableDay day = getByDay(homework.getDay(), homework.getMonth(), homework.getYear());
        if (day == null) {
            return;
        }
        for (int i = 0; i < day.lessonEntities.size(); i++) {
            if (day.lessonEntities.get(i).getLessonTime() == homework.getLesson()) {
                if (homework.getTask().equals(day.lessonEntities.get(i).getHomeWork()))
                    return;
                day.lessonEntities.get(i).setHomeWork(HomeWork.fromTask(homework.getTask()));
                update(day.lessonEntities.get(i));
                return;
            }
        }
    }

    @Transaction
    public void updateTask(DateTask task) {
        LessonAndTimetableDay day = getByDay(task.getDay(), task.getMonth(), task.getYear());
        if (day == null) {
            return;
        }
        for (int i = 0; i < day.lessonEntities.size(); i++) {
            if (day.lessonEntities.get(i).getLessonTime() == task.getLesson()) {
                if (task.getTask().equals(day.lessonEntities.get(i).getTask()))
                    return;
                day.lessonEntities.get(i).setTask(task.getTask());
                update(day.lessonEntities.get(i));
                return;
            }
        }
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
