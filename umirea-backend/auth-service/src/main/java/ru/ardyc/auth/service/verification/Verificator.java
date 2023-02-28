package ru.ardyc.auth.service.verification;

public interface Verificator {

    void sendVerificationCode(String login, String firstName, String lastName, String verificationCode);

    String createVerificationCode(String userToken);

    boolean isRightVerificationCode(String verificationCode, String userToken);

}
