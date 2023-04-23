package edu.mirea.ardyc.umirea.data.model.cloud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


public class CloudFile {
    private String name;
    private String remotePath;
    private String filePath;

    public CloudFile(String name, String remotePath, String filePath) {
        this.name = name;
        this.remotePath = remotePath;
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
