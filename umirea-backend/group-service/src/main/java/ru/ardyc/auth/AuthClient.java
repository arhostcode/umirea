package ru.ardyc.auth;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
public interface AuthClient {
    @GET
    @Path("auth/login")
    @Produces(MediaType.APPLICATION_JSON)
    String login(@QueryParam("login") String login, @QueryParam("password") String password);


    @GET
    @Path("auth/register")
    @Produces(MediaType.APPLICATION_JSON)
    String register(@QueryParam("login") String login, @QueryParam("password") String password, @QueryParam("educationGroup") String educationGroup, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("imageId") String imageId, @QueryParam("role") String role);

    @GET
    @Path("user/getInfo")
    @Produces(MediaType.APPLICATION_JSON)
    String getInfo(@QueryParam("token") String token);


}
