package ru.ardyc;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.smallrye.common.annotation.Blocking;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.ws.rs.GET;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Inject
    Mailer mailer;

    @PostMapping(path = "/send", consumes = "application/json")
    public String sendEmail(@RequestBody MailMessage mailMessage) {
        mailer.send(Mail.withText(mailMessage.getAddress(), mailMessage.getTheme(), mailMessage.getDescription()));
        return "OK";
    }
}
