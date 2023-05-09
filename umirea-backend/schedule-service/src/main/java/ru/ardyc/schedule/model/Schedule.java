package ru.ardyc.schedule.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.json.JsonArray;
import java.util.ArrayList;
import java.util.List;

@RegisterForReflection
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Schedule {

    private List<Day> days = new ArrayList<>();

    public static Schedule fromJson(JsonArray scheduleArray){
        Schedule schedule = new Schedule();
        for (int i = 0; i < scheduleArray.size(); i++) {
            schedule.days.add(Day.fromJson(scheduleArray.getJsonObject(i)));
        }
        return schedule;
    }


}
