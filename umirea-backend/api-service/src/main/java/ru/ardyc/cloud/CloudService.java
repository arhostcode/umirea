package ru.ardyc.cloud;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/cloud")
public class CloudService {

    @Inject
    @RestClient
    CloudClient cloudClient;

    @POST
    @Path("/folder/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    public Response fileUploadInFolder(@MultipartForm MultipartFormDataInput input) {
        return Response.status(301).location(URI.create("http://84.252.128.74:8086/cloud/folder/upload")).build();
    }

    @POST
    @Path("/file/group/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    public Response fileGroupUpload(@MultipartForm MultipartFormDataInput input) {
        return cloudClient.fileGroupUpload(input);
    }

    @POST
    @Path("/folder/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFolder(@QueryParam("userToken") String userToken, @QueryParam("folderName") String folderName) {
        return cloudClient.createFolder(userToken, folderName);
    }

    @POST
    @Path("/folder/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFolder(@QueryParam("userToken") String userToken, @QueryParam("folderUuid") String folderUuid) {
        return cloudClient.deleteFolder(userToken, folderUuid);
    }


    @GET
    @Path("/file/get")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(@QueryParam("userToken") String userToken, @QueryParam("fileUuid") String fileUuid) {
        return cloudClient.getFile(userToken, fileUuid);
    }

    @GET
    @Path("/file/info/get")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFileInfo(@QueryParam("fileUuid") String fileUuid) {
        return cloudClient.getFileInfo(fileUuid);
    }


    @GET
    @Path("/folder/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroupFiles(@QueryParam("userToken") String userToken) {
        return cloudClient.getGroupFiles(userToken);
    }
}
