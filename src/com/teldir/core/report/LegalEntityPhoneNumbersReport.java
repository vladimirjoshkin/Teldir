package com.teldir.core.report;

import com.teldir.core.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class LegalEntityPhoneNumbersReport extends Report {

    public ArrayList<LegalEntity> legalEntities;
    public ArrayList<City> cities;
    public ArrayList<Heading> headings;

    public LegalEntityPhoneNumbersReport() {

    }

    public LegalEntityPhoneNumbersReport(ArrayList<LegalEntity> legalEntities) {
        this.title = "Legal Entity Phone Numbers Report";
        this.columnNames = new String[] {"Legal Entity", "Phone number City, Country", "Heading", "Phone Number"};
        this.legalEntities = legalEntities;
        this.cities = new ArrayList<City>();
        this.headings = new ArrayList<Heading>();
        /* form an ArrayList<City> */
        for(int i = 0; i < legalEntities.size(); i++) {
            if(!arrayListContainsCity(cities, legalEntities.get(i).getAddress().getCity())) {
                cities.add(legalEntities.get(i).getAddress().getCity());
            }
            for(int j = 0; j < legalEntities.get(i).getPhoneNumbers().size(); j++) {
                if(!arrayListContainsHeading(headings, legalEntities.get(i).getPhoneNumbers().get(j).getHeading())) {
                    headings.add(legalEntities.get(i).getPhoneNumbers().get(j).getHeading());
                }
            }
        }
        System.out.println(legalEntities);
        System.out.println(cities);
        System.out.println(headings);
    }

    public boolean arrayListContainsCity(ArrayList<City> cityArrayList, City city) {
        for(int i = 0; i < cityArrayList.size(); i++) {
            if(city.getId() == cityArrayList.get(i).getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean arrayListContainsHeading(ArrayList<Heading> headingArrayList, Heading heading) {
        for(int i = 0; i < headingArrayList.size(); i++) {
            if(heading.getId() == headingArrayList.get(i).getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String formTableContent() {
        String tableContent = "";

        ArrayList<String> orderedListCity = new ArrayList<String>();
        ArrayList<String> orderedListHeading = new ArrayList<String>();
        for(int i = 0; i < legalEntities.size(); i++) {
            if(!orderedListCity.contains(legalEntities.get(i).getAddress().getCity().getName())) {
                orderedListCity.add(legalEntities.get(i).getAddress().getCity().getName());
            }
            for(int j = 0; j < legalEntities.get(i).getPhoneNumbers().size(); j++) {
                if(!orderedListHeading.contains(legalEntities.get(i).getPhoneNumbers().get(j).getHeading().getName())) {
                    orderedListHeading.add(legalEntities.get(i).getPhoneNumbers().get(j).getHeading().getName());
                }
            }
        }
        orderedListCity.sort(Comparator.naturalOrder());
        orderedListHeading.sort(Comparator.naturalOrder());

        ArrayList<PhoneNumber> mobileNumbers = new ArrayList<PhoneNumber>();

        for(int i = 0; i < orderedListCity.size(); i++) {
            for(int j = 0; j < orderedListHeading.size(); j++) {
                for(int k = 0; k < legalEntities.size(); k++) {
                    for(int z = 0; z < legalEntities.get(k).getPhoneNumbers().size(); z++) {
                        LegalEntity legalEntity = legalEntities.get(k);
                        City city = getCity(orderedListCity.get(i));
                        Heading heading = getHeading(orderedListHeading.get(j));
                        PhoneNumber phoneNumber = legalEntity.getPhoneNumbers().get(z);
                        System.out.println(city + " " + heading.getName() + " " + phoneNumber.getNumber() + " " + phoneNumber.getAreaCode() + " " + phoneNumber.getHeading().getName());
                        if(phoneNumber.getAreaCode() == city.getCode() && phoneNumber.getHeading().getId() == heading.getId()) {
                            tableContent += tr(td(legalEntity.getFullName()) + td(city.getName() + ", " + city.getDistrict().getCountry().getName()) + td(heading.getName()) + td(phoneNumber.getNumber()));
                        } else if(Objects.isNull(getCityByCode(phoneNumber.getAreaCode())) && phoneNumber.getHeading().getId() == heading.getId()) {
                            mobileNumbers.add(phoneNumber);
                        }
                    }
                }
            }
        }

        for(int i = 0; i < mobileNumbers.size(); i++) {
            PhoneNumber phoneNumber = mobileNumbers.get(i);
            tableContent += tr(td(getLegalEntityByPhoneNumber(phoneNumber).getFullName()) + td("MOBILE") + td(phoneNumber.getHeading().getName()) + td(phoneNumber.getNumber()));
        }

        return tableContent;
    }

    public Heading getHeading(String name) {
        for(int i = 0; i < headings.size(); i++) {
            if(name.equals(headings.get(i).getName())) {
                return headings.get(i);
            }
        }
        return null;
    }

    public City getCity(String name) {
        for(int i = 0; i < cities.size(); i++) {
            if(name.equals(cities.get(i).getName())) {
                return cities.get(i);
            }
        }
        return null;
    }

    public City getCityByCode(int code) {
        for(int i = 0; i < cities.size(); i++) {
            if(code == cities.get(i).getCode()) {
                return cities.get(i);
            }
        }
        return null;
    }

    public LegalEntity getLegalEntityByPhoneNumber(PhoneNumber phoneNumber) {
        for(int i = 0; i < legalEntities.size(); i++) {
            for(int j = 0; j < legalEntities.get(i).getPhoneNumbers().size(); j++) {
                if(phoneNumber.isSame(legalEntities.get(i).getPhoneNumbers().get(j))) {
                    return legalEntities.get(i);
                }
            }
        }
        return null;
    }
}