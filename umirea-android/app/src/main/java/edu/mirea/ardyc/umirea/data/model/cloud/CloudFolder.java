package edu.mirea.ardyc.umirea.data.model.cloud;

import java.util.ArrayList;
import java.util.List;

public class CloudFolder {
    private String name;
    private String uuid;
    private String groupUuid;
    private List<CloudFile> files = new ArrayList<>();

    public CloudFolder(String uuid, String name) {
        this.name = name;
        this.uuid = uuid;
    }

    public CloudFolder(String name, String uuid, String groupUuid) {
        this.name = name;
        this.uuid = uuid;
        this.groupUuid = groupUuid;
    }


    public CloudFolder(String name, String uuid, String groupUuid, List<CloudFile> files) {
        this.name = name;
        this.uuid = uuid;
        this.groupUuid = groupUuid;
        this.files = files;
    }

    public String getName() {
        return name;
    }

    public List<CloudFile> getFiles() {
        return files;
    }

    public String getUuid() {
        return uuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    @Override
    public String toString() {
        return "CloudFolder{" +
                "folderName='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", groupUuid='" + groupUuid + '\'' +
                ", files=" + files +
                '}';
    }
}
