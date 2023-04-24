package edu.mirea.ardyc.umirea.data.model.timetable;

import androidx.annotation.NonNull;

import java.util.Objects;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.timetable.lessons.LabLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.lessons.LectionLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.lessons.SeminarLesson;

public class Lesson implements Cloneable {

    private int lessonTime = 0;

    private int lessonIconMini = R.drawable.calendar_circle_lection;
    private int lessonIcon = R.drawable.lec_icon;

    private String name = "";
    private String teacherName = "";
    private String room = "";

    private HomeWork homeWork;
    private Task task;


    public Lesson(String name, String teacherName, String room, HomeWork homeWork, Task task, int lessonTime) {
        this.name = name;
        this.teacherName = teacherName;
        this.room = room;
        this.homeWork = homeWork;
        this.task = task;
        this.lessonTime = lessonTime;
    }

    public int getType() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public int getLessonIconMini() {
        return lessonIconMini;
    }

    public int getLessonTime() {
        return lessonTime;
    }

    public String getRoom() {
        return room;
    }

    public HomeWork getHomeWork() {
        return homeWork;
    }

    public Task getTask() {
        return task;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setHomeWork(HomeWork homeWork) {
        this.homeWork = homeWork;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setLessonIconMini(int lessonIconMini) {
        this.lessonIconMini = lessonIconMini;
    }


    public void setLessonTime(int lessonTime) {
        this.lessonTime = lessonTime;
    }

    public int getLessonIcon() {
        return lessonIcon;
    }

    public void setLessonIcon(int lessonIcon) {
        this.lessonIcon = lessonIcon;
    }

    public Lesson() {
    }

    @NonNull
    @Override
    public Lesson clone() {
        try {
            Lesson clone = (Lesson) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static class Builder implements Cloneable {

        private Lesson lesson;

        public Builder() {
            lesson = new Lesson();
        }

        public Builder(Lesson initialLesson) {
            this.lesson = initialLesson;
        }

        public Builder(int lessonType) {
            switch (lessonType) {
                case 1:
                    lesson = new LabLesson();
                    break;
                case 2:
                    lesson = new LectionLesson();
                    break;
                case 3:
                    lesson = new SeminarLesson();
                    break;
                default:
                    lesson = new Lesson();
            }
        }


        public Builder withName(String name) {
            lesson.setName(name);
            return this;
        }

        public Builder withRoom(String room) {
            lesson.setRoom(room);
            return this;
        }

        public Builder withHomework(HomeWork homework) {
            lesson.setHomeWork(homework);
            return this;
        }

        public Builder withTask(Task task) {
            lesson.setTask(task);
            return this;
        }

        public Builder withLessonTime(int lessonTime) {
            lesson.setLessonTime(lessonTime);
            return this;
        }

        public Builder withTeacher(String teacherName) {
            lesson.setTeacherName(teacherName);
            return this;
        }

        public Builder cloned() {
            lesson = lesson.clone();
            return this;
        }

        public Lesson build() {
            return lesson;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return lessonTime == lesson.lessonTime && Objects.equals(name, lesson.name) && Objects.equals(teacherName, lesson.teacherName) && Objects.equals(room, lesson.room) && Objects.equals(homeWork, lesson.homeWork) && Objects.equals(task, lesson.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonTime, name, teacherName, room, homeWork, task);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonTime=" + lessonTime +
                ", lessonIconMini=" + lessonIconMini +
                ", lessonIcon=" + lessonIcon +
                ", name='" + name + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", room='" + room + '\'' +
                ", homeWork=" + homeWork +
                ", task=" + task +
                '}';
    }
}
