package ru.ardyc.schedule.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.stereotype.Service;
import ru.ardyc.response.ErrorResponse;
import ru.ardyc.response.MessageResponse;
import ru.ardyc.response.OutputErrorResponse;
import ru.ardyc.response.Response;
import ru.ardyc.schedule.mireaxyz.MireaXyzClient;
import ru.ardyc.schedule.model.Groups;
import ru.ardyc.schedule.model.Schedule;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.StringReader;

@Service
public class ScheduleService {

    @Inject
    @RestClient
    MireaXyzClient mireaXyzClient;

    public Response getGroupBaseSchedule(String groupName) {
        JsonArray array = Json.createReader(new StringReader(mireaXyzClient.getCertainGroup(groupName))).readArray();
        if (array.size() == 0)
            return new OutputErrorResponse("Group not found");
        JsonArray scheduleArray = array.getJsonObject(0).getJsonArray("schedule");
        Schedule schedule = Schedule.fromJson(scheduleArray);

        return MessageResponse.create(schedule);
    }


    public Response getAllGroups() {
        return MessageResponse.create(Groups.fromJson(Json.createReader(new StringReader(mireaXyzClient.getAllGroups())).readArray()));
    }

}
