package ru.ardyc.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ardyc.auth.entity.UserEntity;
import ru.ardyc.auth.errors.AuthError;
import ru.ardyc.auth.errors.UserNotFoundError;
import ru.ardyc.auth.model.User;
import ru.ardyc.auth.repos.UsersRepository;
import ru.ardyc.response.MessageResponse;
import ru.ardyc.response.Response;
import ru.ardyc.utils.DigestUtils;
import ru.ardyc.utils.RequestUtils;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public Response getUserByToken(String token) {
        if (RequestUtils.isInvalid(token)) {
            return new AuthError("Not all of fields was been provided. Required token.");
        }
        Optional<UserEntity> user = usersRepository.findByToken(token);
        if (user.isEmpty())
            return new UserNotFoundError();
        return MessageResponse.create(User.fromEntity(user.get()));
    }

    public Response setPassword(String token, String oldPassword, String newPassword) {
        if (RequestUtils.isInvalid(token, oldPassword, newPassword)) {
            return new AuthError("Not all of fields was been provided. Required token, oldPassword, newPassword.");
        }
        Optional<UserEntity> optionalUserEntity = usersRepository.findByToken(token);
        if (optionalUserEntity.isEmpty())
            return new UserNotFoundError();
        UserEntity userEntity = optionalUserEntity.get();
        if (!userEntity.getPassword().equals(DigestUtils.encodeMD5(oldPassword))) {
            return new AuthError("Passwords doesn`t match");
        }
        userEntity.setPassword(DigestUtils.encodeMD5(newPassword));
        userEntity.setToken(DigestUtils.encodeMD5(userEntity.getLogin() + newPassword));
        usersRepository.save(userEntity);
        return MessageResponse.create(User.fromEntity(userEntity));
    }

    public Response setRole(String token, String role) {
        if (RequestUtils.isInvalid(token, role)) {
            return new AuthError("Not all of fields was been provided. Required token, role.");
        }
        Optional<UserEntity> optionalUserEntity = usersRepository.findByToken(token);
        if (optionalUserEntity.isEmpty())
            return new UserNotFoundError();
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setRole(role);
        usersRepository.save(userEntity);
        return MessageResponse.create(User.fromEntity(userEntity));
    }

    public Response setGroup(String token, String group) {
        if (RequestUtils.isInvalid(token, group)) {
            return new AuthError("Not all of fields was been provided. Required token, group.");
        }
        Optional<UserEntity> optionalUserEntity = usersRepository.findByToken(token);
        if (optionalUserEntity.isEmpty())
            return new UserNotFoundError();
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setEducationGroup(group);
        usersRepository.save(userEntity);
        return MessageResponse.create(User.fromEntity(userEntity));
    }

}
