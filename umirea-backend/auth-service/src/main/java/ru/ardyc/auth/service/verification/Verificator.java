package ru.ardyc.auth.service.verification;

import org.springframework.stereotype.Component;

public interface Verificator {

    void sendVerificationCode(String login, String firstName, String lastName, String verificationCode);

    String createVerificationCode(String userToken);

    boolean isRightVerificationCode(String verificationCode, String userToken);

}
