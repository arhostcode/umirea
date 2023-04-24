package edu.mirea.ardyc.umirea.data.dataSources.room.dashboard;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.google.gson.Gson;

import edu.mirea.ardyc.umirea.data.model.timetable.HomeWork;
import edu.mirea.ardyc.umirea.data.model.timetable.Task;
@ProvidedTypeConverter
public class DashboardConverter {

    @TypeConverter
    public String homeworkToJson(HomeWork homeWork) {
        return new Gson().toJson(homeWork);
    }
    @TypeConverter
    public HomeWork homeworkFromJson(String json) {
        return new Gson().fromJson(json, HomeWork.class);
    }
    @TypeConverter
    public String noteToJson(Task task) {
        return new Gson().toJson(task);
    }
    @TypeConverter
    public Task noteFromJson(String json) {
        return new Gson().fromJson(json, Task.class);
    }

}
