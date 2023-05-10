package ru.ardyc.cloud.repo;

import org.springframework.data.repository.CrudRepository;
import ru.ardyc.cloud.entity.CloudFolderEntity;

import java.util.List;

public interface CloudFolderRepository extends CrudRepository<CloudFolderEntity, Long> {

    public List<CloudFolderEntity> getCloudFolderEntitiesByGroupUuid(String uuid);

    public CloudFolderEntity getCloudFolderEntityByUuid(String uuid);

    public CloudFolderEntity getCloudFolderEntityByGroupUuidAndName(String uuid, String name);

}
