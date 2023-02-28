package ru.ardyc.auth;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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


}
