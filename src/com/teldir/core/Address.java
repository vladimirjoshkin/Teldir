package com.teldir.core;

import java.util.ArrayList;

class Address {
    // ignore
}

class Country {
    private int id;
    private String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
}

class CountryList {
    private static ArrayList<Country> list;

    public static void add(Country country) {
        list.add(country);
    }

    public static void add(int id, String name) {
        list.add(new Country(id, name));
    }

    public static Country get(int id) {
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return list.get(i);
            }
        }
        return null;
    }
}

class District {
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

    public String getName() {
        return name;
    }
}

class DistrictList {
    private static ArrayList<District> list;

    public static void add(District district) {
        list.add(district);
    }

    public static void add(int id, Country country, String name) {
        list.add(new District(id, country, name));
    }

    public static District get(int id) {
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return list.get(i);
            }
        }
        return null;
    }

    public static District get(String name) {
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i).getName() == name) {
                return list.get(i);
            }
        }
        return null;
    }
}

class City {
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

class CityList {
    private static ArrayList<City> list;

    public static void add(City city) {
        list.add(city);
    }

    public static void add(int id, District district, String name) {
        list.add(new City(id, district, name));
    }
}