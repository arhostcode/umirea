package edu.mirea.ardyc.umirea.data.model.info;

import java.util.UUID;

import edu.mirea.ardyc.umirea.data.model.group.Member;

public class InfoMessage {

    private UUID id;
    private String text;
    private Member owner;

    public InfoMessage(UUID id, String text, Member owner) {
        this.id = id;
        this.text = text;
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Member getOwner() {
        return owner;
    }
}
