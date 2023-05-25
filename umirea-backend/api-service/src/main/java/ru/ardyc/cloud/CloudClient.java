package ru.ardyc.cloud;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@RegisterForReflection
@RegisterRestClient
public interface CloudClient {

    @POST
    @Path("cloud/folder/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fileUploadInFolder(@MultipartForm MultipartFormDataInput input);

    @POST
    @Path("cloud/folder/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFolder(@QueryParam("userToken") String userToken, @QueryParam("folderName") String folderName);

    @POST
    @Path("cloud/file/group/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    public Response fileGroupUpload(@MultipartForm MultipartFormDataInput input);

    @POST
    @Path("cloud/folder/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFolder(@QueryParam("userToken") String userToken, @QueryParam("folderUuid") String folderUuid);


    @GET
    @Path("cloud/file/get")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(@QueryParam("userToken") String userToken, @QueryParam("fileUuid") String fileUuid);

    @GET
    @Path("cloud/file/info/get")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFileInfo(@QueryParam("fileUuid") String fileUuid);

    @GET
    @Path("cloud/folder/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroupFiles(@QueryParam("userToken") String userToken);
}
