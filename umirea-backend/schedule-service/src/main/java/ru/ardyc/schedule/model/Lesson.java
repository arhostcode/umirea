package ru.ardyc.schedule.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.ArrayList;
import java.util.List;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Lesson {
    private List<Integer> weeks = new ArrayList<>();
    private String name;
    private String type;
    private String teacher;
    private String room;


    public Lesson oddWeeks() {
        for (int i = 0; i < 16; i += 2) {
            weeks.add(i);
        }
        return this;
    }

    public Lesson evenWeeks() {
        for (int i = 1; i < 16; i += 2) {
            weeks.add(i);
        }
        return this;
    }

    private Lesson withRoom(String room) {
        this.room = room;
        return this;
    }

    private Lesson withName(String name) {
        this.name = name;
        return this;
    }

    private Lesson withTeacher(String teacher) {
        this.teacher = teacher;
        return this;
    }

    private Lesson withType(String type) {
        this.type = type;
        return this;
    }

    public static Lesson fromJsonEven(JsonObject lessonObject) {
        Lesson lesson = createLessonBase(lessonObject);
        JsonValue weeksValue = lessonObject.get("weeks");
        if (weeksValue.getValueType() != JsonValue.ValueType.NULL) {
            JsonArray weeksArray = lessonObject.getJsonArray("weeks");
            for (int i = 0; i < weeksArray.size(); i++) {
                lesson.weeks.add(weeksArray.getInt(i) - 1);
            }
        } else {
            lesson.evenWeeks();
        }

        return lesson;
    }

    public static Lesson fromJsonOdd(JsonObject lessonObject) {
        Lesson lesson = createLessonBase(lessonObject);
        JsonValue weeksValue = lessonObject.get("weeks");
        if (weeksValue.getValueType() != JsonValue.ValueType.NULL) {
            JsonArray weeksArray = lessonObject.getJsonArray("weeks");
            for (int i = 0; i < weeksArray.size(); i++) {
                lesson.weeks.add(weeksArray.getInt(i) - 1);
            }
        } else {
            lesson.oddWeeks();
        }

        return lesson;
    }

    private static Lesson createLessonBase(JsonObject lessonObject){
        return new Lesson()
                .withName(getFromJsonOrNull(lessonObject, "name"))
                .withTeacher(getFromJsonOrNull(lessonObject, "tutor"))
                .withRoom(getFromJsonOrNull(lessonObject, "place"))
                .withType(getFromJsonOrNull(lessonObject, "type"));
    }

    private static String getFromJsonOrNull(JsonObject jsonObject, String val) {
        JsonValue jsonValue = jsonObject.get(val);
        if (jsonValue.getValueType() == JsonValue.ValueType.NULL)
            return null;
        return jsonObject.getString(val);
    }

}
