package ru.ardyc.schedule.mireaxyz;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RegisterRestClient
public interface MireaXyzClient {
    @GET
    @Path("groups/all")
    @Produces(MediaType.APPLICATION_JSON)
    String getAllGroups();

    @GET
    @Path("groups/certain")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    String getCertainGroup(@QueryParam("name") String name);


    @GET
    @Path("time/startTime")
    @Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
    Response getStartTime();

}
