package ru.ardyc.auth.repos;

import org.springframework.data.repository.CrudRepository;
import ru.ardyc.auth.entity.UserEntity;


import java.util.Optional;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByToken(String token);

}
