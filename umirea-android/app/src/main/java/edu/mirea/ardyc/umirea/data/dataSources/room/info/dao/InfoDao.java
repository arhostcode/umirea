package edu.mirea.ardyc.umirea.data.dataSources.room.info.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.room.info.entities.InfoEntity;

@Dao
public abstract class InfoDao {

    @Query("SELECT * FROM info")
    public abstract List<InfoEntity> getAll();

    @Insert
    public abstract void insert(InfoEntity entity);

    @Query("SELECT * FROM info ORDER BY id DESC LIMIT 1")
    public abstract InfoEntity getLast();

    @Query("DELETE FROM info")
    public abstract void clearAll();
}
