package com.teldir.core;

import java.util.ArrayList;

public class DistrictList {
    private static ArrayList<District> list = new ArrayList<District>();

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

    public static String[] toArray() {
        String[] stringArray = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            stringArray[i] = list.get(i).getName();
        }
        return stringArray;
    }

    private static int countDistricts(Country country) {
        int count = 0;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getCountry().isSame(country)) {
                count += 1;
            }
        }
        return count;
    }

    public static String[] toArray(Country country) {
        String[] stringArray = new String[countDistricts(country)];
        int arrIndex = 0;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getCountry().isSame(country)) {
                stringArray[arrIndex] = list.get(i).getName();
                arrIndex += 1;
            }
        }
        return stringArray;
    }

    /*
    private static int[] toArrayLinear(Country country) {
        int[] intArray = new int[countDistricts(country)];
        int arrIndex = 0;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getCountry().isSame(country)) {
                intArray[arrIndex] = list.get(i).getId();
                arrIndex += 1;
            }
        }
        return intArray;
    }

    public static District getDistrictByLinearSelection(Country country, int linear) {
        int[] linearArray = toArrayLinear(country);
        return get(linearArray[linear]);
    }
    */
}
