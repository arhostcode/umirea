package ru.ardyc.group.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import ru.ardyc.group.entity.MemberEntity;
@RegisterForReflection
public class Member {

    private String uuid;

    private String groupUUID;

    private String imageId;

    public Member(String uuid, String groupUUID, String imageId) {
        this.uuid = uuid;
        this.imageId = imageId;
        this.groupUUID = groupUUID;
    }

    public String getUuid() {
        return uuid;
    }

    public String getGroupUUID() {
        return groupUUID;
    }

    public static Member fromEntity(MemberEntity entity) {
        return new Member(entity.getUuid(), entity.getGroupId(), entity.getImageId());
    }
}
