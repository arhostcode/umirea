package ru.ardyc.schedule;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.web.bind.annotation.*;
import ru.ardyc.response.Response;
import ru.ardyc.schedule.mireaxyz.MireaXyzClient;
import ru.ardyc.schedule.model.Group;
import ru.ardyc.schedule.model.Groups;
import ru.ardyc.schedule.model.Schedule;
import ru.ardyc.schedule.service.ScheduleService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import java.io.StringReader;

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
        return scheduleService.getGroupBaseSchedule(userToken);
    }


}
