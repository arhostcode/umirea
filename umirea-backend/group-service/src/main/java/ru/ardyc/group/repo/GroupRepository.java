package ru.ardyc.group.repo;

import org.springframework.data.repository.CrudRepository;
import ru.ardyc.group.entity.GroupEntity;

public interface GroupRepository extends CrudRepository<GroupEntity, Long> {
}
