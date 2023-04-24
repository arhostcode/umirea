package edu.mirea.ardyc.umirea.data.model.cloud;

import java.util.ArrayList;
import java.util.List;

public class CloudFolder {
    private String folderName;
    private String uuid;
    private final List<CloudFile> files = new ArrayList<>();

    public CloudFolder(String uuid, String folderName) {
        this.folderName = folderName;
        this.uuid = uuid;
    }

    public String getFolderName() {
        return folderName;
    }

    public List<CloudFile> getFiles() {
        return files;
    }

    public String getUuid() {
        return uuid;
    }
}
