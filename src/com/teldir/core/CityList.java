package com.teldir.core;

import java.util.ArrayList;

public class CityList {
    private static ArrayList<City> list;

    public static void add(City city) {
        list.add(city);
    }

    public static void add(int id, District district, String name) {
        list.add(new City(id, district, name));
    }
}
