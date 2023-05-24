package ru.ardyc.schedule;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/schedule")
public class ScheduleService {


    @Inject
    @RestClient
    ScheduleClient scheduleClient;

    @GET
    @Path("/get/groups/all")
    public Response getAllGroups() {
        return scheduleClient.getAllGroups();
    }

    @GET
    @Path("/get/group/base")
    public Response getGroupBaseSchedule(@QueryParam("groupName") String groupName) {
        return scheduleClient.getGroupBaseSchedule(groupName);
    }


    @GET
    @Path("/get/user/base")
    public Response getUserBaseSchedule(@QueryParam("userToken") String userToken) {
        return scheduleClient.getUserBaseSchedule(userToken);
    }


    @GET
    @Path("/user/lessons/add")
    public Response addLessonToUser(@QueryParam("userToken") String userToken, @QueryParam("day") Integer day, @QueryParam("month") Integer month, @QueryParam("year") Integer year, @QueryParam("name") String name, @QueryParam("teacherName") String teacherName, @QueryParam("room") String room, @QueryParam("lessonTime") Integer lessonTime, @QueryParam("lessonType") Integer lessonType) {
        return scheduleClient.addLessonToUser(userToken, day, month, year, name, teacherName, room, lessonTime, lessonType);
    }


    @GET
    @Path("/user/lessons")
    public Response getUserLessons(@QueryParam("userToken") String userToken) {
        return scheduleClient.getUserLessons(userToken);
    }


    @GET
    @Path("/user/homeworks/add")
    public Response addUserHomework(@QueryParam("userToken") String userToken, @QueryParam("day") Integer day, @QueryParam("month") Integer month, @QueryParam("year") Integer year, @QueryParam("text") String text, @QueryParam("lessonTime") Integer lessonTime) {
        return scheduleClient.addUserHomework(userToken, day, month, year, text, lessonTime);
    }


    @GET
    @Path("/user/homeworks")
    public Response getUserHomeworks(@QueryParam("userToken") String userToken) {
        return scheduleClient.getUserHomeworks(userToken);
    }

    @GET
    @Path("/user/notes/add")
    public Response addUserNote(@QueryParam("userToken") String userToken, @QueryParam("day") Integer day, @QueryParam("month") Integer month, @QueryParam("year") Integer year, @QueryParam("text") String text, @QueryParam("lessonTime") Integer lessonTime) {
        return scheduleClient.addUserNote(userToken, day, month, year, text, lessonTime);
    }


    @GET
    @Path("/user/notes")
    public Response getUserNotes(@QueryParam("userToken") String userToken) {
        return scheduleClient.getUserNotes(userToken);
    }


}
