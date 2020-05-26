package com.teldir.core;

public class City {
    private int id;
    private District district;
    private String name;
    private int code;

    public City(int id, District district, String name, int code) {
        this.id = id;
        this.district = district;
        this.name = name;
        this.code = code;
    }

    public String toString() {
        return "{City id=" + id + " name=" + name + " code=" + code + " }";
    }

    public boolean isSame(City city) {
        if (id == city.getId()) {
            return true;
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public District getDistrict() {
        return district;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
