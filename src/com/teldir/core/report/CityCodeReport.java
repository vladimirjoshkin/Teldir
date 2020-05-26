package com.teldir.core.report;

import com.teldir.core.City;

import java.util.*;

public class CityCodeReport extends Report {

    private ArrayList<City> cities;

    public CityCodeReport(ArrayList<City> cities) {
        this.title = "City Code Report";
        this.columnNames = new String[] {"Country", "City", "Phone Code"};
        this.cities = cities;
        System.out.println(cities);
    }

    @Override
    public String formTableContent() {
        String tableContent = "";

        ArrayList<String> orderedList = new ArrayList<String>();
        for(int i = 0; i < cities.size(); i++) {
            orderedList.add(cities.get(i).getName());
        }
        orderedList.sort(Comparator.naturalOrder());
        for(int i = 0; i < orderedList.size(); i++) {
            City city = getCity(orderedList.get(i));
            tableContent += tr(td(city.getDistrict().getCountry().getName()) +
                    td(city.getName()) +
                    td(city.getCode()));
        }
        return tableContent;
    }

    private City getCity(String name) {
        for(int i = 0; i < cities.size(); i++) {
            if(name.equals(cities.get(i).getName())) {
                return cities.get(i);
            }
        }
        return null;
    }
}