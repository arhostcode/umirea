package edu.mirea.ardyc.umirea.data.model.info;

import java.util.UUID;

import edu.mirea.ardyc.umirea.data.model.group.Member;

public class InfoMessage {

    private String id;
    private String name;
    private String text;
    private String ownerId;

    public InfoMessage(String id, String name, String text, String owner) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.ownerId = owner;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getOwner() {
        return ownerId;
    }

    public String getName() {
        return name;
    }
}
