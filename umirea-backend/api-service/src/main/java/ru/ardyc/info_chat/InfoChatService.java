package ru.ardyc.info_chat;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ardyc.cloud.CloudClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class InfoChatService {

    @Inject
    @RestClient
    InfoChatClient infoChatClient;

    @POST
    @Path("chat/send")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(@QueryParam("userToken") String userToken, @RequestBody String text) {
        return infoChatClient.sendMessage(userToken, text);
    }

    @GET
    @Path("chat/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages(@QueryParam("userToken") String userToken, @QueryParam("after") String uuid) {
        return infoChatClient.getMessages(userToken, uuid);
    }

    @POST
    @Path("info/create")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessageInfo(@QueryParam("userToken") String userToken, @QueryParam("name") String name, @RequestBody String text) {
        return infoChatClient.sendMessageInfo(userToken, name, text);
    }

    @GET
    @Path("info/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessagesInfo(@QueryParam("userToken") String userToken, @QueryParam("after") String uuid) {
        return infoChatClient.getMessagesInfo(userToken, uuid);
    }


}
