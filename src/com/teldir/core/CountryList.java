package com.teldir.core;

import java.util.ArrayList;

public class CountryList {
    private static ArrayList<Country> list = new ArrayList<Country>();

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

    public static Country get(String name) {
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                return list.get(i);
            }
        }
        return null;
    }

    public static String[] toArray() {
        String[] stringArray = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            stringArray[i] = list.get(i).getName();
        }
        return stringArray;
    }

    private static int[] toArrayLinear() {
        int[] intArray = new int[list.size()];
        for(int i = 0; i < list.size(); i++) {
            intArray[i] = list.get(i).getId();
        }
        return intArray;
    }

    public static Country getCountryByLinearSelection(int linear) {
        int[] linearArray = toArrayLinear();
        return get(linearArray[linear]);
    }
}
