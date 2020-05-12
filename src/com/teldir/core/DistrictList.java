package com.teldir.core;

import java.util.ArrayList;

public class DistrictList {
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
            if (list.get(i).getName().equals(name)) {
                return list.get(i);
            }
        }
        return null;
    }
}
