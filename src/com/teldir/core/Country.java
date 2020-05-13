package com.teldir.core;

public class Country {
    private int id;
    private String name;

    public Country(int id) {
        this.id = id;
    }

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "{ id=" + id + " name=" + name +" }";
    }

    public boolean isSame(Country country) {
        if (id == country.getId()) {
            return true;
        } else {
            return false;
        }
    }
}
