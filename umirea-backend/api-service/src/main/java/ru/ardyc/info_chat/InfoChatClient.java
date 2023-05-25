package ru.ardyc.info_chat;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RegisterForReflection
@RegisterRestClient
public interface InfoChatClient {

    @POST
    @Path("chat/send")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(@QueryParam("userToken") String userToken, @RequestBody String text);


    @GET
    @Path("chat/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages(@QueryParam("userToken") String userToken, @QueryParam("after") String uuid);


    @POST
    @Path("info/create")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessageInfo(@QueryParam("userToken") String userToken, @QueryParam("name") String name, @RequestBody String text);


    @GET
    @Path("info/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessagesInfo(@QueryParam("userToken") String userToken, @QueryParam("after") String uuid);
}
