package ru.ardyc.schedule;


import org.springframework.web.bind.annotation.*;
import ru.ardyc.response.Response;
import ru.ardyc.schedule.model.DatedLesson;
import ru.ardyc.schedule.model.HomeWork;
import ru.ardyc.schedule.model.Note;
import ru.ardyc.schedule.service.ScheduleService;

import javax.inject.Inject;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Inject
    ScheduleService scheduleService;

    @GetMapping(path = "/get/groups/all")
    public Response getAllGroups() {
        return scheduleService.getAllGroups();
    }

    @GetMapping(path = "/get/group/base")
    public Response getGroupBaseSchedule(@RequestParam String groupName) {
        return scheduleService.getGroupBaseSchedule(groupName);
    }

    @GetMapping(path = "/get/user/base")
    public Response getUserBaseSchedule(@RequestParam String userToken) {
        return scheduleService.getUserBaseSchedule(userToken);
    }

    @GetMapping(path = "/user/lessons/add")
    public Response addLessonToUser(@RequestParam String userToken, @RequestParam Integer day, @RequestParam Integer month, @RequestParam Integer year, @RequestParam String name, @RequestParam String teacherName, @RequestParam String room, @RequestParam Integer lessonTime, @RequestParam Integer lessonType) {
        return scheduleService.addUserLesson(userToken, new DatedLesson("", "", day, month, year, lessonTime, lessonType, name, teacherName, room));
    }


    @GetMapping(path = "/user/lessons")
    public Response getUserLessons(@RequestParam String userToken) {
        return scheduleService.getUserLessons(userToken);
    }

    @GetMapping(path = "/user/homeworks/add")
    public Response addUserHomework(@RequestParam String userToken, @RequestParam Integer day, @RequestParam Integer month, @RequestParam Integer year, @RequestParam String text, @RequestParam Integer lessonTime) {
        return scheduleService.addUserHomework(userToken, new HomeWork("", "", day, month, year, lessonTime, text));
    }


    @GetMapping(path = "/user/homeworks")
    public Response getUserHomeworks(@RequestParam String userToken) {
        return scheduleService.getUserHomework(userToken);
    }

    @GetMapping(path = "/user/notes/add")
    public Response addUserNote(@RequestParam String userToken, @RequestParam Integer day, @RequestParam Integer month, @RequestParam Integer year, @RequestParam String text, @RequestParam Integer lessonTime) {
        return scheduleService.addUserNote(userToken, new Note("", "", day, month, year, lessonTime, text));
    }


    @GetMapping(path = "/user/notes")
    public Response getUserNotes(@RequestParam String userToken) {
        return scheduleService.getUserNotes(userToken);
    }


}
