package edu.mirea.ardyc.umirea.data.dataSources.room.dashboard.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import edu.mirea.ardyc.umirea.data.model.timetable.HomeWork;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Task;

@Entity(tableName = "timetable_lessons")
public class LessonEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name = "timetable_day_id")
    public int timetableDayId;

    @ColumnInfo(name = "time")
    private int lessonTime = 0;

    @ColumnInfo(name = "type")
    private int lessonType = 0;

    @ColumnInfo(name = "name")
    private String name = "";

    @ColumnInfo(name = "teacher")
    private String teacherName = "";

    @ColumnInfo(name = "room")
    private String room = "";

    private HomeWork homeWork;

    private Task task;

    public LessonEntity() {
    }

    @Ignore
    public LessonEntity(int lessonTime, int lessonType, String name, String teacherName, String room, HomeWork homeWork, Task task) {
        this.lessonTime = lessonTime;
        this.lessonType = lessonType;
        this.name = name;
        this.teacherName = teacherName;
        this.room = room;
        this.homeWork = homeWork;
        this.task = task;
    }

    @Ignore
    public LessonEntity(int lessonTime, int lessonType, String name, String teacherName, String room) {
        this.lessonTime = lessonTime;
        this.lessonType = lessonType;
        this.name = name;
        this.teacherName = teacherName;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public int getLessonTime() {
        return lessonTime;
    }

    public int getLessonType() {
        return lessonType;
    }

    public String getName() {
        return name;
    }

    public String getTeacherName() {
        return teacherName;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setLessonTime(int lessonTime) {
        this.lessonTime = lessonTime;
    }

    public void setLessonType(int lessonType) {
        this.lessonType = lessonType;
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

    public static LessonEntity fromLesson(Lesson lesson) {
        return new LessonEntity(lesson.getLessonTime(), lesson.getType(), lesson.getName(), lesson.getTeacherName(), lesson.getRoom(), lesson.getHomeWork(), lesson.getTask());
    }

    public Lesson toLesson() {
        Lesson.Builder builder = new Lesson.Builder(lessonType);
        return builder.withLessonTime(lessonTime).withTeacher(teacherName).withRoom(room).withTask(task).withHomework(homeWork).withName(name).build();
    }

    @Override
    public String toString() {
        return "LessonEntity{" +
                "id=" + id +
                ", lessonTime=" + lessonTime +
                ", lessonType=" + lessonType +
                ", name='" + name + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", room='" + room + '\'' +
                ", homeWork=" + homeWork +
                ", task=" + task +
                '}';
    }
}
