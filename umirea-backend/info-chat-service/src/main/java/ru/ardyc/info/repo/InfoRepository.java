package ru.ardyc.info.repo;

import org.springframework.data.repository.CrudRepository;
import ru.ardyc.info.entity.InfoEntity;

import java.util.List;

public interface InfoRepository extends CrudRepository<InfoEntity, Long> {

    List<InfoEntity> getInfoEntitiesByGroupUuidAndIdAfter(String groupUuid, Long id);

    InfoEntity getInfoEntityByUuid(String uuid);

    List<InfoEntity> getInfoEntitiesByGroupUuid(String groupUuid);

}
