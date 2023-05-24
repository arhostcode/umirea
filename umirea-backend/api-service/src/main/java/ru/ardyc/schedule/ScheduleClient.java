package ru.ardyc.schedule;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@RegisterForReflection
@RegisterRestClient
public interface ScheduleClient {

    @GET
    @Path("schedule/get/groups/all")
    public Response getAllGroups();

    @GET
    @Path("schedule/get/group/base")
    public Response getGroupBaseSchedule(@QueryParam("groupName") String groupName);


    @GET
    @Path("schedule/get/user/base")
    public Response getUserBaseSchedule(@QueryParam("userToken") String userToken);


    @GET
    @Path("schedule/user/lessons/add")
    public Response addLessonToUser(@QueryParam("userToken") String userToken, @QueryParam("day") Integer day, @QueryParam("month") Integer month, @QueryParam("year") Integer year, @QueryParam("name") String name, @QueryParam("teacherName") String teacherName, @QueryParam("room") String room, @QueryParam("lessonTime") Integer lessonTime, @QueryParam("lessonType") Integer lessonType);


    @GET
    @Path("schedule/user/lessons")
    public Response getUserLessons(@QueryParam("userToken") String userToken);


    @GET
    @Path("schedule/user/homeworks/add")
    public Response addUserHomework(@QueryParam("userToken") String userToken, @QueryParam("day") Integer day, @QueryParam("month") Integer month, @QueryParam("year") Integer year, @QueryParam("text") String text, @QueryParam("lessonTime") Integer lessonTime);


    @GET
    @Path("schedule/user/homeworks")
    public Response getUserHomeworks(@QueryParam("userToken") String userToken);

    @GET
    @Path("schedule/user/notes/add")
    public Response addUserNote(@QueryParam("userToken") String userToken, @QueryParam("day") Integer day, @QueryParam("month") Integer month, @QueryParam("year") Integer year, @QueryParam("text") String text, @QueryParam("lessonTime") Integer lessonTime);


    @GET
    @Path("schedule/user/notes")
    public Response getUserNotes(@QueryParam("userToken") String userToken);


}
