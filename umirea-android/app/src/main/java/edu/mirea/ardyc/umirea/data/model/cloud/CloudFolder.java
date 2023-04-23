package edu.mirea.ardyc.umirea.data.model.cloud;

import java.util.ArrayList;
import java.util.List;

public class CloudFolder {
    private String folderName;
    private final List<CloudFile> files = new ArrayList<>();

    public CloudFolder(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }

    public List<CloudFile> getFiles() {
        return files;
    }

}
