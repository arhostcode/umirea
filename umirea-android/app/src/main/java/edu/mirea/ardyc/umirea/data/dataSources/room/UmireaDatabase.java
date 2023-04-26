package edu.mirea.ardyc.umirea.data.dataSources.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.DashboardConverter;
import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.dao.LessonDao;
import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities.LessonEntity;
import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.dao.TimetableDao;
import edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities.TimetableDayEntity;
import edu.mirea.ardyc.umirea.data.dataSources.room.group.dao.MemberDao;
import edu.mirea.ardyc.umirea.data.dataSources.room.group.entities.MemberEntity;

@TypeConverters(DashboardConverter.class)
@Database(entities = {LessonEntity.class, TimetableDayEntity.class, MemberEntity.class}, version = 2, exportSchema = false)
public abstract class UmireaDatabase extends RoomDatabase {

    public abstract LessonDao lessonDao();

    public abstract TimetableDao timetableDao();

    public abstract MemberDao memberDao();

    private static volatile UmireaDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UmireaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UmireaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UmireaDatabase.class, "umirea_database_1").addTypeConverter(new DashboardConverter())
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
