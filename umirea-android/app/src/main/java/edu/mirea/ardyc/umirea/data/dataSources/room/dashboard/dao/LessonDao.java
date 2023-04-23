package edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities.LessonEntity;

@Dao
public interface LessonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(LessonEntity lesson);

    @Query("DELETE FROM timetable_lessons")
    void deleteAll();

    @Query("SELECT * FROM timetable_lessons")
    LiveData<List<LessonEntity>> getLessons();

}
