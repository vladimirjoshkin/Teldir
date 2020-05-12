package com.teldir.core;

import java.util.ArrayList;

public class CountryList {
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
