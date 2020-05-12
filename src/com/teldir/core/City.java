package com.teldir.core;

public class City {
    private int id;
    private District district;
    private String name;

    public City(int id, District district, String name) {
        this.id = id;
        this.district = district;
        this.name = name;
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
}
