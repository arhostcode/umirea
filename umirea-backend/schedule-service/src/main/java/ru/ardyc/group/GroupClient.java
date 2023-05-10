package ru.ardyc.group;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
public interface GroupClient {

    @GET
    @Path("group/get")
    @Produces(MediaType.APPLICATION_JSON)
    String getInfo(@QueryParam("userToken") String token);

}
