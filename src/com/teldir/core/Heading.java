package com.teldir.core;

public class Heading {
    private int id;
    private String name;

    public Heading(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}