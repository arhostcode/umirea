package ru.ardyc.group.repo;

import org.springframework.data.repository.CrudRepository;
import ru.ardyc.group.entity.MemberEntity;

import java.util.List;

public interface MemberRepository extends CrudRepository<MemberEntity, Long> {

    public List<MemberEntity> getMemberEntitiesByGroupId(String groupId);

    public MemberEntity getMemberEntityByUuid(String uuid);

}
