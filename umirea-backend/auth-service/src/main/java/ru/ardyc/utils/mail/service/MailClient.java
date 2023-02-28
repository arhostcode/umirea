package ru.ardyc.utils.mail.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import ru.ardyc.utils.mail.Mail;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
public interface MailClient {

    @POST
    @Path("mail/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    String sendMail(Mail mail);

}
