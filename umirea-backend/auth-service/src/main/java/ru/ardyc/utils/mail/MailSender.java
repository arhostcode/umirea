package ru.ardyc.utils.mail;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;
import ru.ardyc.utils.mail.service.MailClient;

import javax.inject.Inject;

public class MailSender {

    private MailClient mailClient;

    public MailSender(MailClient mailClient) {
        this.mailClient = mailClient;
    }

    public void sendMail(Mail mail) {
        mailClient.sendMail(mail);
    }
}
