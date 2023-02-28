package ru.ardyc.auth.service.verification.mail;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ardyc.auth.service.verification.Verificator;
import ru.ardyc.utils.VerificationUtils;
import ru.ardyc.utils.mail.Mail;
import ru.ardyc.utils.mail.MailSender;
import ru.ardyc.utils.mail.service.MailClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

public class MailVerificator implements Verificator {
    private MailSender mailVerificationSender;

    public MailVerificator(MailClient mailClient) {
        this.mailVerificationSender = new MailSender(mailClient);
    }

    @Override
    public void sendVerificationCode(String login, String firstName, String lastName, String verificationCode) {
        mailVerificationSender.sendMail(new Mail(login, "Verification code", verificationCode));
    }

    @Override
    public String createVerificationCode(String userToken) {
        return VerificationUtils.createVerificationCode(userToken);
    }

    @Override
    public boolean isRightVerificationCode(String verificationCode, String userToken) {
        return createVerificationCode(userToken).equals(verificationCode);
    }


}
