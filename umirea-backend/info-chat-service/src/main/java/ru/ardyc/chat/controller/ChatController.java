package ru.ardyc.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ardyc.chat.service.ChatService;
import ru.ardyc.response.OutputErrorResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @POST
    @Path("/send")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(@QueryParam("userToken") String userToken, @RequestBody String text) {
        if (text == null || userToken == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new OutputErrorResponse("Необходимо указать userToken, text")).build();
        }
        return Response.ok().entity(chatService.sendMessage(text, userToken)).build();
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages(@QueryParam("userToken") String userToken, @QueryParam("after") String uuid) {
        if (userToken == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new OutputErrorResponse("Необходимо указать userToken")).build();
        }
        return Response.ok().entity(chatService.getMessagesAfter(userToken, uuid)).build();
    }


}
