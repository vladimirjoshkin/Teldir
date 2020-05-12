package com.teldir.client.standalone;

import com.teldir.core.*;

public class DBInterfaceProvider {

    public static void initializeData() {
        initializeCountryList();
        initializeDistrictList();
        initializeCityList();
    }

    private static void initializeCountryList() {
        CountryList.add(643, "Russia");
        CountryList.add(417, "Kyrgyzstan");
        CountryList.add(428, "Latvia");
        CountryList.add(398, "Kazakhstan");
        CountryList.add(112, "Belarus");
        CountryList.add(840, "United States");
        CountryList.add(826, "United Kingdom");
    }

    private static void initializeDistrictList() {
        DistrictList.add(64301, CountryList.get(643), "Irkutsk district");
        DistrictList.add(64302, CountryList.get(643), "Moscow");
        DistrictList.add(64303, CountryList.get(643), "St. Peterburg");

        DistrictList.add(41701, CountryList.get(417), "Bishkek");

        DistrictList.add(42801, CountryList.get(428), "Ilukstes novads");
        DistrictList.add(42802, CountryList.get(428), "Daugavpils novads");
        DistrictList.add(42802, CountryList.get(428), "Riga");
    }

    private static void initializeCityList() {
        CityList.add(643011, DistrictList.get(64301), "Irkutsk city");
        CityList.add(643021, DistrictList.get(64302), "Moscow city");
        CityList.add(643031, DistrictList.get(64303), "St. Peterburg");

        CityList.add(417011, DistrictList.get(41701), "Bishek city");

        CityList.add(428011, DistrictList.get(42801), "Ilukste");
        CityList.add(428021, DistrictList.get(42802), "Daugavpils");
        CityList.add(428031, DistrictList.get(42803), "Riga");
    }

    public static String[] getCountryNames() {
        return CountryList.toArray();
    }

    public static Country getCountryByLinearSelection(int linear) {
        return CountryList.getCountryByLinearSelection(linear);
    }

    public static String[] getDistrictNamesByLinearSelection(int linear) {
        return DistrictList.toArray(CountryList.getCountryByLinearSelection(linear));
    }

    public static String[] getCityNameByLinearSelection(Country country, int linear) {
        return CityList.toArray(DistrictList.getDistrictByLinearSelection(country, linear));
    }

    public static void saveNaturalPerson(NaturalPerson naturalPerson) {
        
    }

    public static NaturalPerson getNaturalPerson(int id) {
        return new NaturalPerson(-1);
    }

    public static void saveLegalEntity(LegalEntity legalEntity) {

    }

    public static LegalEntity getLegalEntity(int id) {
        return new LegalEntity(-1);
    }
}