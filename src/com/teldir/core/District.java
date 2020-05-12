package com.teldir.core;

public class District {
    private int id;
    private Country country;
    private String name;

    public District(int id, Country country, String name) {
        this.id = id;
        this.country = country;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public boolean isSame(District district) {
        if(id == district.id && country.isSame(district.getCountry()) && name.equals(district.getName())) {
            return true;
        } else {
            return false;
        }
    }
}
