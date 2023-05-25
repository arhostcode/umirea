package ru.ardyc.auth;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RegisterForReflection
@RegisterRestClient
public interface AuthClient {
    @GET
    @Path("auth/login")
    @Produces(MediaType.APPLICATION_JSON)
    Response login(@QueryParam("login") String login, @QueryParam("password") String password);


    @GET
    @Path("auth/register")
    @Produces(MediaType.APPLICATION_JSON)
    Response register(@QueryParam("login") String login, @QueryParam("password") String password, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("verificationCode") String verificationCode);

    @GET
    @Path("auth/verify")
    @Produces(MediaType.APPLICATION_JSON)
    Response verify(@QueryParam("login") String login, @QueryParam("password") String password, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName);

    @GET
    @Path("auth/verifyReset")
    @Produces(MediaType.APPLICATION_JSON)
    Response verifyResetPassword(@QueryParam("login") String login);

    @GET
    @Path("auth/reset")
    @Produces(MediaType.APPLICATION_JSON)
    Response resetPassword(@QueryParam("login") String login, @QueryParam("newPassword") String newPassword, @QueryParam("verificationCode") String verificationCode);

    @GET
    @Path("user/getInfo")
    @Produces(MediaType.APPLICATION_JSON)
    Response getInfo(@QueryParam("token") String token);
}
