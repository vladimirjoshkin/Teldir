package com.teldir.core;

public class Owner {
    public static final int NATURAL_PERSON = 10;
    public static final int LEGAL_ENTITY = 20;

    private int id;
    private int ownership;
    private int ownerId;

    public Owner(int id, int ownership, int ownerId) {
        this.id = id;
        this.ownership = ownership;
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public int getOwnership() {
        return ownership;
    }

    public int getOwnerId() {
        return ownerId;
    }
}