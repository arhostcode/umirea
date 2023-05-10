package ru.ardyc.chat.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ardyc.chat.entity.MessageEntity;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessageEntity, Long> {

    List<MessageEntity> getMessageEntitiesByGroupUuidAndIdAfter(String groupUuid, Long id);

    MessageEntity getMessageEntityByUuid(String uuid);

    List<MessageEntity> getMessageEntitiesByGroupUuid(String groupUuid);

}
