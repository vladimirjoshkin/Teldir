package com.teldir.core;

public class Address {
    private int id;
    private String index;
    private City city;
    private String street;
    private String building;

    public Address(int id, String index, City city, String street, String building) {
        this.id = id;
        this.index = index;
        this.city = city;
        this.street = street;
        this.building = building;
    }

    public int getId() {
        return id;
    }

    public String getIndex() {
        return index;
    }

    public City getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getBuilding() {
        return building;
    }

    public String toString() {
        return index + ", " + city.getDistrict().getCountry().getName() + ", " + city.getDistrict().getName() + ", "+ city.getName() + ", " + street + ", " + building;
    }
}

