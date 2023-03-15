package ru.ardyc.auth.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ardyc.auth.entity.UserEntity;
import ru.ardyc.auth.errors.AuthError;
import ru.ardyc.auth.errors.NotQualifiedPasswordError;
import ru.ardyc.auth.errors.NotSuccessfulVerificationError;
import ru.ardyc.auth.errors.UserNotFoundError;
import ru.ardyc.auth.model.User;
import ru.ardyc.auth.repos.UsersRepository;
import ru.ardyc.auth.service.verification.Verificator;
import ru.ardyc.auth.service.verification.mail.MailVerificator;
import ru.ardyc.response.MessageResponse;
import ru.ardyc.response.Response;
import ru.ardyc.utils.DigestUtils;
import ru.ardyc.utils.RequestUtils;
import ru.ardyc.utils.mail.service.MailClient;


import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private Verificator verificator;
    @Autowired
    private UsersRepository usersRepository;

    public AuthService(@RestClient MailClient mailClient) {
        verificator = new MailVerificator(mailClient);
    }

    public Response auth(String login, String password) {
        if (RequestUtils.isInvalid(login, password)) {
            return new AuthError("Not all of fields was been provided. Required login, password.");
        }
        Optional<UserEntity> user = usersRepository.findByLogin(login);
        if (user.isEmpty()) {
            return new AuthError("This user doesn`t exists.");
        }
        if (user.get().getPassword().equals(DigestUtils.encodeMD5(password))) {
            return MessageResponse.create(User.fromEntity(user.get()));
        }

        return new AuthError("Password is not correct.");
    }

    public Response register(String login, String password, String educationGroup, String firstName, String lastName, String role, String verificationCode) {
        if (RequestUtils.isInvalid(login, password, lastName, firstName, verificationCode)) {
            return new AuthError("Not all of fields was been provided. Required login, password, firstName, lastname, verificationCode.");
        }
        if (usersRepository.findByLogin(login).isPresent()) {
            return new AuthError("This login is already used.");
        }
        if (!verificator.isRightVerificationCode(verificationCode, DigestUtils.encodeMD5(login + password))) {
            return new NotSuccessfulVerificationError();
        }
        UserEntity user = new UserEntity(firstName, lastName, login, DigestUtils.encodeMD5(password), educationGroup, DigestUtils.encodeMD5(login + password), UUID.randomUUID().toString(), role);
        usersRepository.save(user);
        return MessageResponse.create(User.fromEntity(user));
    }

    public Response verify(String login, String password, String firstName, String lastName) {
        if (RequestUtils.isInvalid(login, password, lastName, firstName)) {
            return new AuthError("Not all of fields was been provided. Required login, password, firstName, lastname.");
        }
        String verificationCode = verificator.createVerificationCode(DigestUtils.encodeMD5(login + password));
        verificator.sendVerificationCode(login, firstName, lastName, verificationCode);
        return MessageResponse.create("Verification code is successfully sent.");
    }

    public Response verifyResetPassword(String login) {
        if (RequestUtils.isInvalid(login)) {
            return new AuthError("Not all of fields was been provided. Required login.");
        }
        Optional<UserEntity> optionalUserEntity = usersRepository.findByLogin(login);
        if (optionalUserEntity.isEmpty()) {
            return new UserNotFoundError();
        }
        String verificationCode = verificator.createVerificationCode(optionalUserEntity.get().getToken());
        verificator.sendVerificationCode(login, optionalUserEntity.get().getFirstName(), optionalUserEntity.get().getLastName(), verificationCode);
        return MessageResponse.create("Verification code is successfully sent.");
    }

    public Response resetPassword(String login, String newPassword, String verificationCode) {
        if (RequestUtils.isInvalid(login, newPassword, verificationCode)) {
            return new AuthError("Not all of fields was been provided. Required login, newPassword, verificationCode.");
        }
        Optional<UserEntity> optionalUserEntity = usersRepository.findByLogin(login);
        if (optionalUserEntity.isEmpty()) {
            return new UserNotFoundError();
        }
        if (!isQualifiedPassword(newPassword)) {
            return new NotQualifiedPasswordError();
        }
        String originalVerificationCode = verificator.createVerificationCode(optionalUserEntity.get().getToken());
        if (!originalVerificationCode.equals(verificationCode)) {
            return new NotSuccessfulVerificationError();
        }

        UserEntity entity = optionalUserEntity.get();
        entity.setPassword(DigestUtils.encodeMD5(newPassword));
        entity.setToken(DigestUtils.encodeMD5(login + newPassword));

        usersRepository.save(entity);
        return MessageResponse.create("Password was been reseted.");
    }

    public Response deleteAccount(String token) {
        Optional<UserEntity> user = usersRepository.findByToken(token);
        if (user.isEmpty()) {
            return new UserNotFoundError();
        }
        usersRepository.delete(user.get());
        return new Response();
    }

    private boolean isQualifiedPassword(String password) {
        return password.length() > 8;
    }
}
