package ru.ardyc.cloud.repo;

import org.springframework.data.repository.CrudRepository;
import ru.ardyc.cloud.entity.CloudFileEntity;

public interface CloudFilesRepository extends CrudRepository<CloudFileEntity, Long> {

    public CloudFileEntity getCloudFileEntityByUuid(String uuid);


}
