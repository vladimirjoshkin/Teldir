package com.teldir.core.report;

import com.teldir.core.City;
import com.teldir.core.NaturalPerson;

import java.util.ArrayList;
import java.util.Comparator;

public class CityNaturalPersonPhoneNumbersReport extends Report {

    private ArrayList<NaturalPerson> naturalPersons;

    public CityNaturalPersonPhoneNumbersReport(ArrayList<NaturalPerson> naturalPersons, City city) {
        this.title = "Natural Person Phone Numbers Report" + " (" + city.getName() + ")";
        this.columnNames = new String[] {"Natural Person", "Phone Numbers"};
        this.naturalPersons = naturalPersons;
        System.out.println(naturalPersons);
    }

    @Override
    public String formTableContent() {
        String tableContent = "";

        ArrayList<String> orderedList = new ArrayList<String>();
        for(int i = 0; i < naturalPersons.size(); i++) {
            orderedList.add(naturalPersons.get(i).getFamilyName());
        }
        orderedList.sort(Comparator.naturalOrder());
        for(int i = 0; i < orderedList.size(); i++) {
            NaturalPerson naturalPerson = getNaturalPerson(orderedList.get(i));
            tableContent += tr(td(naturalPerson.getFamilyName() + " " + naturalPerson.getFirstName() + " " + naturalPerson.getPatronymic()) +
                    td(naturalPerson.getPhoneNumbersAsString()));
        }
        return tableContent;
    }

    private NaturalPerson getNaturalPerson(String familyName) {
        for(int i = 0; i < naturalPersons.size(); i++) {
            if(familyName.equals(naturalPersons.get(i).getFamilyName())) {
                return naturalPersons.get(i);
            }
        }
        return null;
    }
}