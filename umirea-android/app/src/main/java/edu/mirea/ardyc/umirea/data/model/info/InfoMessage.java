package edu.mirea.ardyc.umirea.data.model.info;

public class InfoMessage {

    private String uuid;
    private String name;
    private String message;
    private String ownerUuid;

    public InfoMessage(String uuid, String name, String message, String owner) {
        this.uuid = uuid;
        this.name = name;
        this.message = message;
        this.ownerUuid = owner;
    }

    public String getUuid() {
        return uuid;
    }

    public String getMessage() {
        return message;
    }

    public String getOwner() {
        return ownerUuid;
    }

    public String getName() {
        return name;
    }
}
