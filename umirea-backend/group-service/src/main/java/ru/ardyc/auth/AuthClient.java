package ru.ardyc.auth;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
public interface AuthClient {

    @GET
    @Path("user/getInfo")
    @Produces(MediaType.APPLICATION_JSON)
    String getInfo(@QueryParam("token") String token);

    @GET
    @Path("user/getInfoByUUID")
    @Produces(MediaType.APPLICATION_JSON)
    String getInfoByUUID(@QueryParam("uuid") String uuid);

    @POST
    @Path("user/setGroup")
    void setGroup(@QueryParam("token") String token, @QueryParam("group") String group);


}
