package com.teldir.core.report;

import com.teldir.core.City;
import com.teldir.core.Heading;
import com.teldir.core.LegalEntity;

import java.util.ArrayList;

public class SpecificLegalEntityPhoneNumberReport extends LegalEntityPhoneNumbersReport {

    public SpecificLegalEntityPhoneNumberReport(ArrayList<LegalEntity> legalEntities, City city, Heading heading) {
        this.title = "Phone Numbers Report of<br>Legal Entities in " + city.getName() + " in field of " + heading.getName();
        this.columnNames = new String[] {"Legal Entity", "Phone number City, Country", "Heading", "Phone Number"};
        this.legalEntities = legalEntities;
        this.cities = new ArrayList<City>();
        this.cities.add(city);
        this.headings = new ArrayList<Heading>();
        this.headings.add(heading);

        System.out.println(legalEntities);
        System.out.println(cities);
        System.out.println(headings);
    }
}