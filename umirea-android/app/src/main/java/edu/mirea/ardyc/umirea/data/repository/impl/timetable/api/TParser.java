package edu.mirea.ardyc.umirea.data.repository.impl.timetable.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class TParser {

    public static List<TWeek> fromJson(JsonObject schedule) {
        JsonArray days = schedule.getAsJsonObject("message").getAsJsonArray("days");
        List<TWeek> weeks = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            weeks.add(new TWeek());
        }
        for (int i = 0; i < days.size(); i++) {
            JsonObject jsonDay = days.get(i).getAsJsonObject();
            JsonArray oddDayLessons = jsonDay.getAsJsonArray("odd");
            for (int j = 0; j < oddDayLessons.size(); j++) {
                if (oddDayLessons.get(j).getAsJsonArray().size() > 0) {
                    JsonObject jsonLesson = oddDayLessons.get(j).getAsJsonArray().get(0).getAsJsonObject();
                    JsonArray jsonWeeks = jsonLesson.getAsJsonArray("weeks");
                    for (int k = 0; k < jsonWeeks.size(); k++) {
                        weeks.get(jsonWeeks.get(k).getAsInt()).getDays().get(i).getLessons().set(j, parseLesson(jsonLesson));
                    }
                }
            }
            JsonArray evenDayLessons = jsonDay.getAsJsonArray("even");
            for (int j = 0; j < evenDayLessons.size(); j++) {
                if (evenDayLessons.get(j).getAsJsonArray().size() > 0) {
                    JsonObject jsonLesson = evenDayLessons.get(j).getAsJsonArray().get(0).getAsJsonObject();
                    JsonArray jsonWeeks = jsonLesson.getAsJsonArray("weeks");
                    for (int k = 0; k < jsonWeeks.size(); k++) {
                        List<TLesson> lessons = weeks.get(jsonWeeks.get(k).getAsInt()).getDays().get(i).getLessons();
                        if (lessons.get(j) == null || jsonWeeks.size() < 8)
                            lessons.set(j, parseLesson(jsonLesson));

                    }
                }
            }

        }

        return weeks;
    }

    private static TLesson parseLesson(JsonObject lesson) {

        String name = lesson.get("name").isJsonNull() ? "" : lesson.get("name").getAsString();
        String teacherName = lesson.get("teacher").isJsonNull() ? "" : lesson.get("teacher").getAsString();
        String type = lesson.get("type").isJsonNull() ? "" : lesson.get("type").getAsString();
        String room = lesson.get("room").isJsonNull() ? "" : lesson.get("room").getAsString();

        return new TLesson(name, type, teacherName, room);

    }
}
