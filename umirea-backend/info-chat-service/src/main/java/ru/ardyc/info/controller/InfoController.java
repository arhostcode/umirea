package ru.ardyc.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ardyc.info.model.Info;
import ru.ardyc.info.service.InfoService;
import ru.ardyc.response.OutputErrorResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/info")
public class InfoController {

    @Autowired
    InfoService infoService;

    @POST
    @Path("/create")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createInfo(@QueryParam("userToken") String userToken, @QueryParam("name") String name, @RequestBody String text) {
        if (name == null || text == null || userToken == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new OutputErrorResponse("Необходимо указать name, userToken, text")).build();
        }
        return Response.ok().entity(infoService.createInfo(name, text, userToken)).build();
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fileUpload(@QueryParam("userToken") String userToken, @QueryParam("after") String uuid) {
        if (userToken == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new OutputErrorResponse("Необходимо указать userToken")).build();
        }
        return Response.ok().entity(infoService.getInfoAfter(userToken, uuid)).build();
    }


}
