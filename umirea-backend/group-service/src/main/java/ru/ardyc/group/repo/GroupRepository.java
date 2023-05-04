package ru.ardyc.group.repo;

import org.springframework.data.repository.CrudRepository;
import ru.ardyc.group.entity.GroupEntity;

import java.util.List;

public interface GroupRepository extends CrudRepository<GroupEntity, Long> {

    public GroupEntity getGroupEntityByUuid(String uuid);

    public GroupEntity getGroupEntityByNameAndToken(String name, String token);
}
