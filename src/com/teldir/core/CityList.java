package com.teldir.core;

import java.util.ArrayList;

public class CityList {
    private static ArrayList<City> list = new ArrayList<City>();

    public static void add(City city) {
        list.add(city);
    }

    public static void add(int id, District district, String name) {
        list.add(new City(id, district, name));
    }

    public static City get(int id) {
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return list.get(i);
            }
        }
        return null;
    }

    public static City get(String name) {
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

    private static int countCities(Country country) {
        int count = 0;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getDistrict().getCountry().isSame(country)) {
                count += 1;
            }
        }
        return count;
    }

    public static String[] toArray(Country country) {
        String[] stringArray = new String[countCities(country)];
        int arrIndex = 0;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getDistrict().getCountry().isSame(country)) {
                stringArray[arrIndex] = list.get(i).getName();
                arrIndex += 1;
            }
        }
        return stringArray;
    }

    private static int countCities(District district) {
        int count = 0;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getDistrict().isSame(district)) {
                count += 1;
            }
        }
        return count;
    }

    public static String[] toArray(District district) {
        String[] stringArray = new String[countCities(district)];
        int arrIndex = 0;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getDistrict().isSame(district)) {
                stringArray[arrIndex] = list.get(i).getName();
                arrIndex += 1;
            }
        }
        return stringArray;
    }
    /*
    private static int[] toArrayLinear(District district) {
        int[] intArray = new int[countCities(district)];
        int arrIndex = 0;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getDistrict().isSame(district)) {
                intArray[arrIndex] = list.get(i).getId();
                arrIndex += 1;
            }
        }
        return intArray;
    }

    public static City getDistrictByLinearSelection(District district, int linear) {
        int[] linearArray = toArrayLinear(district);
        return get(linearArray[linear]);
    }
    */
}
