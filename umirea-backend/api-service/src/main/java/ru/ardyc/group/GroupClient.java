package ru.ardyc.group;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@RegisterForReflection
@RegisterRestClient
public interface GroupClient {

    @GET
    @Path("group/create")
    public Response create(@QueryParam("userToken") String userToken, @QueryParam("groupTimeTable") String groupTimeTable, @QueryParam("groupName") String groupName);

    @GET
    @Path("group/connect")
    public Response connect(@QueryParam("userToken") String userToken, @QueryParam("groupName") String groupName, @QueryParam("groupToken") String groupToken);

    @GET
    @Path("group/disconnect")
    public Response disconnect(@QueryParam("userToken") String userToken);

    @POST
    @Path("group/kick")
    public Response kick(@QueryParam("userToken") String userToken, @QueryParam("kickUserUuid") String kickUserUuid);

    @POST
    @Path("group/set/role")
    public Response setRole(@QueryParam("userToken") String userToken, @QueryParam("userUuid") String userUuid, @QueryParam("role") String role);

    @DELETE
    @Path("group/delete")
    public void delete(@QueryParam("userToken") String userToken, @QueryParam("groupName") String groupName, @QueryParam("groupToken") String groupToken);


    @GET
    @Path("group/get")
    public Response getUserGroup(@QueryParam("userToken") String userToken);

    @GET
    @Path("group/get/members")
    public Response getUserGroupMembers(@QueryParam("userToken") String userToken);


    @POST
    @Path("group/change/schedule")
    public void changeGroupSchedule(@QueryParam("userToken") String userToken, @QueryParam("groupSchedule") String groupSchedule);


}
