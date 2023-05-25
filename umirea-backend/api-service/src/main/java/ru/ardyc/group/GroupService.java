package ru.ardyc.group;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import ru.ardyc.schedule.ScheduleClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/group")
public class GroupService {

    @Inject
    @RestClient
    GroupClient groupClient;

    @GET
    @Path("/create")
    public Response create(@QueryParam("userToken") String userToken, @QueryParam("groupTimeTable") String groupTimeTable, @QueryParam("groupName") String groupName) {
        return groupClient.create(userToken, groupTimeTable, groupName);
    }

    @GET
    @Path("/connect")
    public Response connect(@QueryParam("userToken") String userToken, @QueryParam("groupName") String groupName, @QueryParam("groupToken") String groupToken) {
        return groupClient.connect(userToken, groupName, groupToken);
    }

    @GET
    @Path("/disconnect")
    public Response disconnect(@QueryParam("userToken") String userToken) {
        return groupClient.disconnect(userToken);
    }

    @POST
    @Path("/kick")
    public Response kick(@QueryParam("userToken") String userToken, @QueryParam("kickUserUuid") String kickUserUuid) {
        return groupClient.kick(userToken, kickUserUuid);
    }

    @POST
    @Path("/set/role")
    public Response setRole(@QueryParam("userToken") String userToken, @QueryParam("userUuid") String userUuid, @QueryParam("role") String role) {
        return groupClient.setRole(userToken, userUuid, role);
    }

    @DELETE
    @Path("/delete")
    public void delete(@QueryParam("userToken") String userToken, @QueryParam("groupName") String groupName, @QueryParam("groupToken") String groupToken) {
        groupClient.delete(userToken, groupName, groupToken);
    }


    @GET
    @Path("/get")
    public Response getUserGroup(@QueryParam("userToken") String userToken) {
        return groupClient.getUserGroup(userToken);
    }

    @GET
    @Path("/get/members")
    public Response getUserGroupMembers(@QueryParam("userToken") String userToken) {
        return groupClient.getUserGroupMembers(userToken);
    }


    @POST
    @Path("/change/schedule")
    public void changeGroupSchedule(@QueryParam("userToken") String userToken, @QueryParam("groupSchedule") String groupSchedule) {
        groupClient.changeGroupSchedule(userToken, groupSchedule);
    }


}
