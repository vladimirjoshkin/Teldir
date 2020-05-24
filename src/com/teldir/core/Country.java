package com.teldir.core;

public class Country {
    private int id;
    private String name;
    private int code;

    public Country(int id, String name, int code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public String toString() {
        return "{Country id=" + id + " name=" + name + " code=" + code + " }";
    }

    public boolean isSame(Country country) {
        if (id == country.getId()) {
            return true;
        } else {
            return false;
        }
    }
}
