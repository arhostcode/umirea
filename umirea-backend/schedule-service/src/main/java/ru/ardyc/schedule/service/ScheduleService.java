package ru.ardyc.schedule.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.stereotype.Service;
import ru.ardyc.auth.AuthClient;
import ru.ardyc.auth.User;
import ru.ardyc.group.Group;
import ru.ardyc.group.GroupClient;
import ru.ardyc.response.MessageResponse;
import ru.ardyc.response.OutputErrorResponse;
import ru.ardyc.response.Response;
import ru.ardyc.schedule.entities.DatedLessonEntity;
import ru.ardyc.schedule.entities.HomeWorkEntity;
import ru.ardyc.schedule.entities.NoteEntity;
import ru.ardyc.schedule.mireaxyz.MireaXyzClient;
import ru.ardyc.schedule.model.*;
import ru.ardyc.schedule.repo.DatedLessonsRepository;
import ru.ardyc.schedule.repo.HomeworkRepository;
import ru.ardyc.schedule.repo.NoteRepository;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Inject
    @RestClient
    MireaXyzClient mireaXyzClient;

    @Inject
    @RestClient
    AuthClient authClient;

    @Inject
    @RestClient
    GroupClient groupClient;

    @Inject
    DatedLessonsRepository lessonsRepository;

    @Inject
    HomeworkRepository homeworkRepository;

    @Inject
    NoteRepository noteRepository;


    public Response getGroupBaseSchedule(String groupName) {
        String sch = "";

        File file = new File("schedules", groupName);
        if (file.exists()) {
            try {
                sch = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8)).readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Load from api");
            sch = mireaXyzClient.getCertainGroup(groupName);
            try {
                file.createNewFile();
                PrintWriter writer = new PrintWriter(file);
                writer.println(sch);
                writer.flush();
                writer.close();
            } catch (IOException e) {
            }
        }
        JsonArray array = Json.createReader(new StringReader(sch)).readArray();
        if (array.size() == 0)
            return new OutputErrorResponse("Группа не найдена");
        JsonArray scheduleArray = array.getJsonObject(0).getJsonArray("schedule");
        Schedule schedule = Schedule.fromJson(scheduleArray);

        String[] date = getStartDates();

        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);

        FullSchedule fullSchedule = new FullSchedule(day, month, year, schedule);

        return MessageResponse.create(fullSchedule);
    }

    public Response getUserBaseSchedule(String userToken) {
        Group group = Group.fromJson(groupClient.getInfo(userToken));
        if (group == null)
            return new OutputErrorResponse("Группа не найдена");
        if (group.getTimeTable() == null)
            return new OutputErrorResponse("Расписание не найдено");
        return getGroupBaseSchedule(group.getTimeTable());
    }


    private String[] getStartDates() {
        String responseStr = "";
        File file = new File("start-dates");
        if (file.exists()) {
            try {
                responseStr = new Scanner(file).nextLine();
            } catch (FileNotFoundException e) {
            }
        } else {
            javax.ws.rs.core.Response response = mireaXyzClient.getStartTime();
            response.getHeaders().clear();
            responseStr = response.readEntity(String.class);
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.println(responseStr);
                writer.flush();
                writer.close();
            } catch (FileNotFoundException e) {
            }
        }

        String[] date = responseStr.split("T")[0].split("-");
        return date;
    }

    public Response getAllGroups() {
        return new MessageResponse(Arrays.stream(new File("schedules").listFiles()).map(File::getName).collect(Collectors.toList()));
//        return MessageResponse.create(Groups.fromJson(Json.createReader(new StringReader(mireaXyzClient.getAllGroups())).readArray()));
    }

    public Response addUserLesson(String userToken, DatedLesson datedLesson) {
        User user = User.fromJson(authClient.getInfo(userToken));
        if (user == null)
            return new OutputErrorResponse("Пользователь не найден");
        DatedLessonEntity lesson = lessonsRepository.getDatedLessonEntityByDayAndMonthAndYearAndLessonTimeAndUserUuid(datedLesson.getDay(), datedLesson.getMonth(), datedLesson.getYear(), datedLesson.getLessonTime(), user.getUniqueID());
        if (lesson != null) {
            lesson.setLessonType(datedLesson.getLessonType());
            lesson.setName(datedLesson.getName());
            lesson.setTeacherName(datedLesson.getTeacherName());
            lesson.setRoom(datedLesson.getRoom());
            lessonsRepository.save(lesson);
        } else {
            lessonsRepository.save(new DatedLessonEntity(UUID.randomUUID().toString(), user.getUniqueID(), datedLesson.getDay(), datedLesson.getMonth(), datedLesson.getYear(), datedLesson.getLessonTime(), datedLesson.getLessonType(), datedLesson.getName(), datedLesson.getTeacherName(), datedLesson.getRoom()));
        }
        return MessageResponse.create("Данные успешно сохранены");
    }


    public Response getUserLessons(String userToken) {
        User user = User.fromJson(authClient.getInfo(userToken));
        if (user == null)
            return new OutputErrorResponse("Пользователь не найден");
        List<DatedLessonEntity> lessons = lessonsRepository.getDatedLessonEntitiesByUserUuid(user.getUniqueID());
        return MessageResponse.create(lessons.stream().map(DatedLesson::fromEntity).collect(Collectors.toList()));
    }


    public Response addUserHomework(String userToken, HomeWork homeWork) {
        User user = User.fromJson(authClient.getInfo(userToken));
        if (user == null)
            return new OutputErrorResponse("Пользователь не найден");
        if (user.getEducationGroup() == null || user.getEducationGroup().equals("null")) {
            return new OutputErrorResponse("Группа не найдена");
        }
        HomeWorkEntity homeWorkEntity = homeworkRepository.getHomeWorkEntityByDayAndMonthAndYearAndLessonTimeAndGroupUuid(homeWork.getDay(), homeWork.getMonth(), homeWork.getYear(), homeWork.getLessonTime(), user.getEducationGroup());
        if (homeWorkEntity != null) {
            homeWorkEntity.setText(homeWork.getText());
            homeworkRepository.save(homeWorkEntity);
        } else {
            homeworkRepository.save(new HomeWorkEntity(UUID.randomUUID().toString(), user.getEducationGroup(), homeWork.getDay(), homeWork.getMonth(), homeWork.getYear(), homeWork.getLessonTime(), homeWork.getText()));
        }
        return MessageResponse.create("Данные успешно сохранены");
    }

    public Response getUserHomework(String userToken) {
        User user = User.fromJson(authClient.getInfo(userToken));
        if (user == null)
            return new OutputErrorResponse("Пользователь не найден");
        if (user.getEducationGroup() == null || user.getEducationGroup().equals("null")) {
            return new OutputErrorResponse("Группа не найдена");
        }
        List<HomeWorkEntity> homeWorkEntities = homeworkRepository.getHomeWorkEntitiesByGroupUuid(user.getEducationGroup());
        return MessageResponse.create(homeWorkEntities.stream().map(HomeWork::fromEntity).collect(Collectors.toList()));
    }


    public Response addUserNote(String userToken, Note note) {
        User user = User.fromJson(authClient.getInfo(userToken));
        if (user == null)
            return new OutputErrorResponse("Пользователь не найден");

        NoteEntity noteEntity = noteRepository.getNoteEntityByDayAndMonthAndYearAndLessonTimeAndUserUuid(note.getDay(), note.getMonth(), note.getYear(), note.getLessonTime(), user.getUniqueID());
        if (noteEntity != null) {
            noteEntity.setText(note.getText());
            noteRepository.save(noteEntity);
        } else {
            noteRepository.save(new NoteEntity(UUID.randomUUID().toString(), user.getUniqueID(), note.getDay(), note.getMonth(), note.getYear(), note.getLessonTime(), note.getText()));
        }
        return MessageResponse.create("Данные успешно сохранены");
    }

    public Response getUserNotes(String userToken) {
        User user = User.fromJson(authClient.getInfo(userToken));
        if (user == null)
            return new OutputErrorResponse("Пользователь не найден");

        List<NoteEntity> noteEntities = noteRepository.getNoteEntitiesByUserUuid(user.getUniqueID());
        return MessageResponse.create(noteEntities.stream().map(Note::fromEntity).collect(Collectors.toList()));
    }
}
