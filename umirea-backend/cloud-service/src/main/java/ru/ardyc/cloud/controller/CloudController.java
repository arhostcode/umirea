package ru.ardyc.cloud.controller;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ardyc.cloud.model.CloudFile;
import ru.ardyc.cloud.service.CloudService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("/cloud")
public class CloudController {

    @Autowired
    CloudService cloudService;

    @POST
    @Path("/folder/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    public Response fileUploadInFolder(@MultipartForm MultipartFormDataInput input) {
        return Response.ok().entity(cloudService.uploadFileInFolder(input)).build();
    }

    // Inner api
    @POST
    @Path("/file/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    public Response fileUpload(@MultipartForm MultipartFormDataInput input) {
        return Response.ok().entity(cloudService.uploadFile(input)).build();
    }

    @POST
    @Path("/file/group/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    public Response fileGroupUpload(@MultipartForm MultipartFormDataInput input) {
        return Response.ok().entity(cloudService.uploadGroupFile(input)).build();
    }

    @POST
    @Path("/folder/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFolder(@QueryParam("userToken") String userToken, @QueryParam("folderName") String folderName) {
        return Response.ok().entity(cloudService.createFolder(userToken, folderName)).build();
    }

    @POST
    @Path("/folder/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFolder(@QueryParam("userToken") String userToken, @QueryParam("folderUuid") String folderUuid) {
        return Response.ok().entity(cloudService.deleteFolder(userToken, folderUuid)).build();
    }


    // Inner api
    @POST
    @Path("/file/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFile(@QueryParam("userToken") String userToken, @QueryParam("fileUuid") String fileUuid) {
        return Response.ok().entity(cloudService.deleteFile(userToken, fileUuid)).build();
    }


    @GET
    @Path("/file/get")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(@QueryParam("userToken") String userToken, @QueryParam("fileUuid") String fileUuid) {
        InputStream input = cloudService.getFile(userToken, fileUuid);
        if (input == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().entity(input).build();
    }

    @GET
    @Path("/file/info/get")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFileInfo(@QueryParam("fileUuid") String fileUuid) {
        return Response.ok().entity(cloudService.getFileInfo(fileUuid)).build();
    }


    @GET
    @Path("/folder/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroupFiles(@QueryParam("userToken") String userToken) {
        return Response.ok().entity(cloudService.getGroupFolders(userToken)).build();
    }

}
