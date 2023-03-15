package ru.ardyc.schedule.mireaxyz;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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

}
