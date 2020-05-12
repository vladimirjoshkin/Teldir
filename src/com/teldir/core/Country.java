package com.teldir.core;

public class Country {
    private int id;
    private String name;

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

    public boolean isSame(Country country) {
        if (id == country.getId() && name.equals(country.getName())) {
            return true;
        } else {
            return false;
        }
    }
}
