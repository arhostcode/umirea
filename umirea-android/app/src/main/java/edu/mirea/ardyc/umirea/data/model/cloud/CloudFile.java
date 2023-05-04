package edu.mirea.ardyc.umirea.data.model.cloud;

public class CloudFile {

    private String uuid;
    private String name;
    private String folderId;

    public CloudFile(String uuid, String name, String folderId) {
        this.name = name;
        this.folderId = folderId;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getFolderId() {
        return folderId;
    }


    public String getUuid() {
        return uuid;
    }
}
