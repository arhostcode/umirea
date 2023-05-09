package ru.ardyc.schedule.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
@RegisterForReflection
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Day {

    private String name;
    private List<List<Lesson>> odd = new ArrayList<>();
    private List<List<Lesson>> even = new ArrayList<>();

    public static Day fromJson(JsonObject dayObject) {
        Day day = new Day();
        day.name = dayObject.getString("day");

        JsonArray evenLesson = dayObject.getJsonArray("even");
        addLessonsToList(day.even, evenLesson, true);

        JsonArray oddLesson = dayObject.getJsonArray("odd");
        addLessonsToList(day.odd, oddLesson, false);

        return day;
    }

    private static void addLessonsToList(List<List<Lesson>> list, JsonArray dayObject, boolean isEven) {
        for (int i = 0; i < dayObject.size(); i++) {
            JsonArray l = dayObject.getJsonArray(i);
            List<Lesson> dayLesson = new ArrayList<>();
            for (int j = 0; j < l.size(); j++) {
                if (isEven)
                    dayLesson.add(Lesson.fromJsonEven(l.getJsonObject(j)));
                else
                    dayLesson.add(Lesson.fromJsonOdd(l.getJsonObject(j)));
            }
            list.add(dayLesson);
        }
    }

    public String getName() {
        return name;
    }

}
