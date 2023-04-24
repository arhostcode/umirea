package edu.mirea.ardyc.umirea.data.model.cloud;

public class CloudFile {

    private String uuid;
    private String name;
    private String remotePath;
    private String filePath;

    public CloudFile(String uuid, String name, String remotePath, String filePath) {
        this.name = name;
        this.remotePath = remotePath;
        this.filePath = filePath;
        this.uuid = uuid;
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

    public String getUuid() {
        return uuid;
    }
}
