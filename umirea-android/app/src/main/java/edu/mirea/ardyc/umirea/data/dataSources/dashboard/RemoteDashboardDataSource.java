package edu.mirea.ardyc.umirea.data.dataSources.dashboard;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.DataSource;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.model.group.Member;
import edu.mirea.ardyc.umirea.data.model.info.InfoMessage;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Task;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableMonth;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateTask;
import edu.mirea.ardyc.umirea.data.model.timetable.lessons.LectionLesson;
import edu.mirea.ardyc.umirea.data.net.dashboard.DashboardRemoteService;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.api.TLesson;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.api.TParser;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.api.TWeek;
import edu.mirea.ardyc.umirea.ui.view.AppActivity;

public class RemoteDashboardDataSource extends DataSource {

    private final DashboardRemoteService dashboardRemoteService;

    public RemoteDashboardDataSource(Context context, DashboardRemoteService dashboardRemoteService) {
        super(context);
        this.dashboardRemoteService = dashboardRemoteService;
    }

    public DataResponse<Timetable> getData(String userToken) {
        Timetable.Builder timetableBuilder = new Timetable.Builder();

        try {
            JsonObject response = dashboardRemoteService.getBaseUserSchedule(userToken).execute().body();
            JsonElement code = response.get("code");
            JsonElement message = response.get("message");
            if (code.getAsInt() != 0) {
                return new DataResponse<>(null, message.getAsString());
            }
            if (message != null) {
                int firstMonth = message.getAsJsonObject().get("firstMonth").getAsInt();
                int firstDay = message.getAsJsonObject().get("firstDay").getAsInt();
                int year = message.getAsJsonObject().get("year").getAsInt();

                List<TWeek> weekList = TParser.fromJson(message.getAsJsonObject().get("schedule").getAsJsonObject());
                int week = 0;
                int dayOfWeek = LocalDate.of(year, firstMonth, firstDay).getDayOfWeek().ordinal();
                while (week < weekList.size()) {
                    TimetableMonth.Builder timetableMonth = new TimetableMonth.Builder(firstMonth, year);
                    YearMonth month = YearMonth.of(year, firstMonth);

                    for (int j = firstDay - 1; j < month.lengthOfMonth(); j++) {
                        int LAST_DAY_OF_WEEK = 7;
                        if (dayOfWeek == LAST_DAY_OF_WEEK) {
                            dayOfWeek = 0;
                            week++;
                        }
                        if (week == weekList.size())
                            break;
                        TimetableDay.Builder tiBuilder = new TimetableDay.Builder().withDate(j + 1, firstMonth, year);
                        List<TLesson> lessons = weekList.get(week).getDays().get(dayOfWeek).getLessons();

                        for (int i = 0; i < lessons.size(); i++) {
                            TLesson tLesson = lessons.get(i);
                            if (tLesson != null)
                                tiBuilder.addLesson(new Lesson.Builder(new LectionLesson()).withName(tLesson.getName()).withRoom(tLesson.getRoom()).withTeacher(tLesson.getTeacherName()).withLessonTime(i).build());
                        }

                        dayOfWeek++;
                        timetableMonth.addDay(tiBuilder.build());
                    }
                    firstDay = 1;
                    timetableBuilder.addMonth(timetableMonth.build());
                    firstMonth++;
                }
            } else {
                return new DataResponse<>(null, "Ошибка загрузки основного расписания");
            }
        } catch (Exception e) {
            return new DataResponse<>(null, "Ошибка загрузки основного расписания");
        }
        return new DataResponse<>(timetableBuilder.build());
    }

    public DataResponse<List<String>> getAllGroups() {
        try {
            JsonObject response = dashboardRemoteService.getAllGroups().execute().body();
            JsonElement code = response.get("code");
            JsonElement message = response.get("message");
            if (code.getAsInt() != 0) {
                return new DataResponse<>(null, message.getAsString());
            }
            List<String> list = new ArrayList<>();
            for (JsonElement name : message.getAsJsonArray()) {
                list.add(name.getAsString());
            }
            return new DataResponse<>(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(null, "Ошибка всех групп");
        }
    }

    public int getDashboardHash(String userToken) {
        try {
            JsonObject main = dashboardRemoteService.getBaseUserSchedule(userToken).execute().body();
            return main.toString().hashCode();
        } catch (Exception e) {
            return 0;
        }
    }

    public DataResponse<List<DateLesson>> getAddonLessons(String userToken) {
        try {
            JsonObject response = dashboardRemoteService.getUserLessons(userToken).execute().body();
            JsonElement code = response.get("code");
            JsonElement message = response.get("message");
            if (code.getAsInt() != 0) {
                return new DataResponse<>(null, message.getAsString());
            }

            List<DateLesson> list = new ArrayList<>();
            for (JsonElement element : message.getAsJsonArray()) {
                JsonObject object = element.getAsJsonObject();
                DateLesson dateLesson = new DateLesson(object.get("day").getAsInt(), object.get("month").getAsInt(), object.get("year").getAsInt(),
                        new Lesson.Builder(object.get("lessonType").getAsInt())
                                .withLessonTime(object.get("lessonTime").getAsInt())
                                .withName(object.get("name").getAsString())
                                .withTeacher(object.get("teacherName").getAsString())
                                .withRoom(object.get("room").getAsString())
                                .build());
                list.add(dateLesson);
            }
            return new DataResponse<>(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(null, "Ошибка загрузки личного расписания");
        }
    }

    public DataResponse<String> addLessons(String userToken, List<DateLesson> dateLessons) {
        try {
            for (DateLesson dateLesson : dateLessons) {
                JsonObject response = dashboardRemoteService.addUserLesson(userToken, dateLesson.getDay(), dateLesson.getMonth(), dateLesson.getYear(), dateLesson.getLesson().getName(), dateLesson.getLesson().getTeacherName(), dateLesson.getLesson().getRoom(), dateLesson.getLesson().getLessonTime(), dateLesson.getLesson().getType()).execute().body();
                JsonElement code = response.get("code");
                JsonElement message = response.get("message");
                if (code.getAsInt() != 0) {
                    return new DataResponse<>(null, message.getAsString());
                }
            }
            return new DataResponse<>("Пары добавлены");
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(null, "Ошибка загрузки личного расписания");
        }
    }

    public DataResponse<List<DateTask>> getHomeworks(String userToken) {
        try {
            JsonObject response = dashboardRemoteService.getUserHomeworks(userToken).execute().body();
            JsonElement code = response.get("code");
            JsonElement message = response.get("message");
            if (code.getAsInt() != 0) {
                return new DataResponse<>(null, message.getAsString());
            }

            List<DateTask> list = new ArrayList<>();
            for (JsonElement element : message.getAsJsonArray()) {
                JsonObject object = element.getAsJsonObject();
                DateTask dateLesson = new DateTask(
                        object.get("day").getAsInt(),
                        object.get("month").getAsInt(),
                        object.get("year").getAsInt(),
                        object.get("lessonTime").getAsInt(),
                        new Task(object.get("text").getAsString()));
                list.add(dateLesson);
            }
            return new DataResponse<>(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(null, "Ошибка загрузки домашних заданий");
        }
    }

    public DataResponse<String> addHomework(String userToken, DateTask homework) {
        try {
            JsonObject response = dashboardRemoteService.addUserHomework(userToken, homework.getDay(), homework.getMonth(), homework.getYear(), homework.getLesson(), homework.getTask().getDescription()).execute().body();
            JsonElement code = response.get("code");
            JsonElement message = response.get("message");
            if (code.getAsInt() != 0) {
                return new DataResponse<>(null, message.getAsString());
            }

            return new DataResponse<>("Домашнее задание добавлено");
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(null, "Ошибка загрузки домашних заданий");
        }
    }

    public DataResponse<List<DateTask>> getNotes(String userToken) {
        try {
            JsonObject response = dashboardRemoteService.getUserNotes(userToken).execute().body();
            JsonElement code = response.get("code");
            JsonElement message = response.get("message");
            if (code.getAsInt() != 0) {
                return new DataResponse<>(null, message.getAsString());
            }

            List<DateTask> list = new ArrayList<>();
            for (JsonElement element : message.getAsJsonArray()) {
                JsonObject object = element.getAsJsonObject();
                DateTask dateLesson = new DateTask(
                        object.get("day").getAsInt(),
                        object.get("month").getAsInt(),
                        object.get("year").getAsInt(),
                        object.get("lessonTime").getAsInt(),
                        new Task(object.get("text").getAsString()));
                list.add(dateLesson);
            }
            return new DataResponse<>(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(null, "Ошибка загрузки личного расписания");
        }
    }

    public DataResponse<String> addNote(String userToken, DateTask note) {
        try {
            JsonObject response = dashboardRemoteService.addUserNote(userToken, note.getDay(), note.getMonth(), note.getYear(), note.getLesson(), note.getTask().getDescription()).execute().body();
            JsonElement code = response.get("code");
            JsonElement message = response.get("message");
            if (code.getAsInt() != 0) {
                return new DataResponse<>(null, message.getAsString());
            }
            return new DataResponse<>("Заметка добавлена");
        } catch (Exception e) {
            e.printStackTrace();
            return new DataResponse<>(null, "Ошибка загрузки заметок заданий");
        }
    }
}
