package com.teldir.client.standalone;

import com.teldir.core.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DBInterfaceProvider {

    private static Connection conn = null;
    public static void connect() {
        String url = "jdbc:sqlite:databases/default.db";
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("SQLite connection successful.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static HashMap<String, Integer> getCountries() {
        HashMap<String, Integer> countries = new HashMap<String, Integer>();
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM country");
            while(result.next()) {
                countries.put(result.getString("name"), result.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return countries;
    }

    public static ArrayList<Country> getCountriesArrayList() {
        ArrayList<Country> countries = new ArrayList<Country>();
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM country");
            while(result.next()) {
                countries.add(new Country(result.getInt("id"), result.getString("name"), result.getInt("code")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return countries;
    }

    public static Country getCountry(int id) {
        Country country = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM country WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            country = new Country(result.getInt("id"), result.getString("name"), result.getInt("country_code"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return country;
    }

    public static Country getCountryByCode(int code) {
        Country country = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM country WHERE country_code=?");
            statement.setInt(1, code);
            ResultSet result = statement.executeQuery();
            country = new Country(result.getInt("id"), result.getString("name"), result.getInt("country_code"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return country;
    }

    public static HashMap<String, Integer> getDistricts(int countryId) {
        HashMap<String, Integer> districts = new HashMap<String, Integer>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM district WHERE country_ref=?");
            statement.setInt(1, countryId);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                districts.put(result.getString("name"), result.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return districts;
    }

    public static District getDistrict(int id) {
        District district = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM district WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            district = new District(result.getInt("id"), getCountry(result.getInt("country_ref")), result.getString("name"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return district;
    }


    public static HashMap<String, Integer> getCities(int districtId) {
        HashMap<String, Integer> cities = new HashMap<String, Integer>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM city WHERE district_ref=?");
            statement.setInt(1, districtId);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                cities.put(result.getString("name"), result.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cities;
    }

    public static HashMap<String, Integer> getCountryCities(int countryId) {
        HashMap<String, Integer> districts = getDistricts(countryId);
        HashMap<String, Integer> cities = new HashMap<String, Integer>();
        for (int districtId : districts.values()) {
            try {
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM city WHERE district_ref=?");
                statement.setInt(1, districtId);
                ResultSet result = statement.executeQuery();
                while(result.next()) {
                    cities.put(result.getString("name"), result.getInt("id"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return cities;
    }

    public static ArrayList<City> getCountryCitiesArrayList(int countryId) {
        HashMap<String, Integer> districts = getDistricts(countryId);
        ArrayList<City> cities = new ArrayList<City>();
        for (int districtId : districts.values()) {
            try {
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM city WHERE district_ref=?");
                statement.setInt(1, districtId);
                ResultSet result = statement.executeQuery();
                cities.add(new City(result.getInt("id"), getDistrict(result.getInt("district_ref")), result.getString("name"), result.getInt("area_code")));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return cities;
    }

    public static City getCity(int id) {
        City city = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM city WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            city = new City(result.getInt("id"), getDistrict(result.getInt("district_ref")), result.getString("name"), result.getInt("area_code"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return city;
    }

    public static Address saveAddress(String index, int cityId, String street, String building) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO address (post_index, city_ref, street, building) VALUES (?, ?, ?, ?)");
            statement.setString(1, index);
            statement.setInt(2, cityId);
            statement.setString(3, street);
            statement.setString(4, building);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Address address = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM address WHERE city_ref=? AND street=? AND building=?");
            statement.setInt(1, cityId);
            statement.setInt(1, cityId);
            statement.setString(2, street);
            statement.setString(3, building);
            ResultSet result = statement.executeQuery();
            address = getAddress(result.getInt("id"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return address;
    }

    public static Address updateAddress(int id, String index, int cityId, String street, String building) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE address SET post_index=?, city_ref=?, street=?, building=? WHERE id=?");
            statement.setString(1, index);
            statement.setInt(2, cityId);
            statement.setString(3, street);
            statement.setString(4, building);
            statement.setInt(5, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getAddress(id);
    }

    public static Address getAddress(int id) {
        Address address = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM address WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            address = new Address(result.getInt("id"), result.getString("post_index"), getCity(result.getInt("city_ref")), result.getString("street"), result.getString("building"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return address;
    }

    public static ResultSet getNaturalPersons() {
        ResultSet result = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM natural_person");
            result = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static Owner saveOwner(NaturalPerson naturalPerson) {
        Owner owner = null;
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO ownership (natural_person_ref) VALUES (?)");
            statement.setInt(1, naturalPerson.getId());
            System.out.println("saveOwner npId=" + naturalPerson.getId());
            statement.executeUpdate();
            owner = getOwner(naturalPerson);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return owner;
    }

    public static Owner saveOwner(LegalEntity legalEntity) {
        Owner owner = null;
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO ownership (legal_entity_ref) VALUES (?)");
            statement.setInt(1, legalEntity.getId());
            System.out.println("saveOwner leId=" + legalEntity.getId());
            statement.executeUpdate();
            owner = getOwner(legalEntity);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return owner;
    }

    public static NaturalPerson saveNaturalPerson(String firstName, String familyName, String patronymic, String dateOfBirdth, Address address) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO natural_person (first_name, family_name, patronymic, date_of_birth, address_ref) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, firstName);
            statement.setString(2, familyName);
            statement.setString(3, patronymic);
            statement.setString(4, dateOfBirdth);
            statement.setInt(5, address.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        NaturalPerson naturalPerson = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM natural_person WHERE first_name=? AND family_name=? AND patronymic=? AND date_of_birth=? AND address_ref=?");
            statement.setString(1, firstName);
            statement.setString(2, familyName);
            statement.setString(3, patronymic);
            statement.setString(4, dateOfBirdth);
            statement.setInt(5, address.getId());
            ResultSet result = statement.executeQuery();
            int naturalPersonId = result.getInt("id");
            naturalPerson = getNaturalPersonWithoutOwner(naturalPersonId);
            saveOwner(naturalPerson);
            naturalPerson = getNaturalPerson(naturalPersonId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return naturalPerson;
    }

    public static LegalEntity saveLegalEntity(String name, Address address) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO legal_entity (name, address_ref) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setInt(2, address.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        LegalEntity legalEntity = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM legal_entity WHERE name=? AND address_ref=?");
            statement.setString(1, name);
            statement.setInt(2, address.getId());
            ResultSet result = statement.executeQuery();
            int legalEntityId = result.getInt("id");
            legalEntity = getLegalEntityWithoutOwner(legalEntityId);
            saveOwner(legalEntity);
            legalEntity = getLegalEntity(legalEntityId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return legalEntity;
    }

    public static void updateLegalEntity(int id, String name) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE legal_entity SET name=? WHERE id=?");
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static NaturalPerson getNaturalPerson(int id) {
        NaturalPerson naturalPerson = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM natural_person WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            int naturalPersonId = result.getInt("id");
            Owner owner = getOwnerByNaturalPersonId(naturalPersonId);
            if(Objects.isNull(owner)) {
                System.out.println("owner is null at " + getNaturalPersonWithoutOwner(naturalPersonId).getFullName() + " ID=" + naturalPersonId);
            }
            naturalPerson = new NaturalPerson(result.getInt("id"),
                    result.getString("first_name"),
                    result.getString("family_name"),
                    result.getString("patronymic"),
                    result.getString("date_of_birth"),
                    getAddress(result.getInt("address_ref")),
                    owner,
                    getPhoneNumbers(owner));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return naturalPerson;
    }

    public static NaturalPerson updateNaturalPerson(int id, String firstName, String familyName, String patronymic, String dateOfBirth) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE natural_person SET first_name=?, family_name=?, patronymic=?, date_of_birth=? WHERE id=?");
            statement.setString(1, firstName);
            statement.setString(2, familyName);
            statement.setString(3, patronymic);
            statement.setString(4, dateOfBirth);
            statement.setInt(5, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getNaturalPerson(id);
    }

    private static NaturalPerson getNaturalPersonWithoutOwner(int id) {
        NaturalPerson naturalPerson = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM natural_person WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            naturalPerson = new NaturalPerson(result.getInt("id"),
                    result.getString("first_name"),
                    result.getString("family_name"),
                    result.getString("patronymic"),
                    result.getString("date_of_birth"),
                    getAddress(result.getInt("address_ref")));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return naturalPerson;
    }

    public static Heading getHeading(int id) {
        Heading heading = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM heading WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            heading = new Heading(result.getInt("id"), result.getString("name"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return heading;
    }

    public static HashMap<String, Integer> getHeadings() {
        HashMap<String, Integer> headings = new HashMap<String, Integer>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM heading");
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                headings.put(result.getString("name"), result.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return headings;
    }

    public static void saveHeading(String name) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO heading (name) VALUES (?)");
            statement.setString(1, name);
            System.out.println("saveHeading name=" + name);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Owner getOwner(int id) {
        Owner owner = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM ownership WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if(Objects.nonNull(result.getInt("natural_person_ref"))) {
                owner = new Owner(result.getInt("id"), Owner.NATURAL_PERSON, result.getInt("natural_person_ref"));
            } else {
                owner = new Owner(result.getInt("id"), Owner.LEGAL_ENTITY, result.getInt("legal_entity_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return owner;
    }

    public static Owner getOwnerByNaturalPersonId(int naturalPersonId) {
        Owner owner = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM ownership WHERE natural_person_ref=?");
            statement.setInt(1, naturalPersonId);
            ResultSet result = statement.executeQuery();
            owner = new Owner(result.getInt("id"), Owner.NATURAL_PERSON, result.getInt("natural_person_ref"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return owner;
    }

    public static Owner getOwnerByLegalEntityId(int legalEntityId) {
        Owner owner = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM ownership WHERE legal_entity_ref=?");
            statement.setInt(1, legalEntityId);
            ResultSet result = statement.executeQuery();
            owner = new Owner(result.getInt("id"), Owner.LEGAL_ENTITY, result.getInt("legal_entity_ref"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return owner;
    }

    public static Owner getOwner(NaturalPerson naturalPerson) {
        Owner owner = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM ownership WHERE natural_person_ref=?");
            statement.setInt(1, naturalPerson.getId());
            ResultSet result = statement.executeQuery();
            owner = new Owner(result.getInt("id"), Owner.NATURAL_PERSON, result.getInt("natural_person_ref"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return owner;
    }

    public static Owner getOwner(LegalEntity legalEntity) {
        Owner owner = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM ownership WHERE legal_entity_ref=?");
            statement.setInt(1, legalEntity.getId());
            ResultSet result = statement.executeQuery();
            owner = new Owner(result.getInt("id"), Owner.LEGAL_ENTITY, result.getInt("legal_entity_ref"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return owner;
    }

    public static ArrayList<PhoneNumber> getPhoneNumbers(Owner owner) {
        ArrayList<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM phone_number WHERE ownership_ref=?");
            statement.setInt(1, owner.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                phoneNumbers.add(new PhoneNumber(result.getInt("id"), getHeading(result.getInt("heading_ref")), owner, result.getString("number")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return phoneNumbers;
    }

    public static ArrayList<PhoneNumber> getPhoneNumbers() {
        ArrayList<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM phone_number");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                phoneNumbers.add(new PhoneNumber(result.getInt("id"), getHeading(result.getInt("heading_ref")), getOwner(result.getInt("ownership_ref")), result.getString("number")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return phoneNumbers;
    }

    public static PhoneNumber getPhoneNumber(String number) {
        PhoneNumber phoneNumber = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM phone_number WHERE number=?");
            statement.setString(1, number);
            ResultSet result = statement.executeQuery();
            phoneNumber = new PhoneNumber(result.getInt("id"), getHeading(result.getInt("heading_ref")), getOwner(result.getInt("ownership_ref")), result.getString("number"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return phoneNumber;
    }

    public static void savePhoneNumber(Heading heading, Owner owner, String number) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO phone_number (heading_ref, number, ownership_ref) VALUES (?, ?, ?)");
            statement.setInt(1, heading.getId());
            statement.setString(2, number);
            statement.setInt(3, owner.getId());
            System.out.println("savePhoneNumber heading=" + heading.toString() + " number=" + number + " owner=" + owner.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updatePhoneNumber(int id, Heading heading, Owner owner, String number) {
        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE phone_number SET heading_ref=?, number=?, ownership_ref=? WHERE id=?");
            statement.setInt(1, heading.getId());
            statement.setString(2, number);
            statement.setInt(3, owner.getId());
            statement.setInt(4, id);
            System.out.println("updatePhoneNumber id=" + id + "heading=" + heading.toString() + " number=" + number + " owner=" + owner.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deletePhoneNumber(int id) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM phone_number WHERE id=?");
            statement.setInt(1, id);
            System.out.println("deletePhoneNumber id=" + id + "}");
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static LegalEntity getLegalEntity(int id) {
        LegalEntity legalEntity = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM legal_entity WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            int legalEntityId = result.getInt("id");
            Owner owner = getOwnerByLegalEntityId(legalEntityId);
            if(Objects.isNull(owner)) {
                System.out.println("owner is null at " + getLegalEntityWithoutOwner(legalEntityId).getFullName() + " ID=" + legalEntityId);
            }
            legalEntity = new LegalEntity(result.getInt("id"),
                    result.getString("name"),
                    getAddress(result.getInt("address_ref")),
                    owner,
                    getPhoneNumbers(owner));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return legalEntity;
    }

    private static LegalEntity getLegalEntityWithoutOwner(int id) {
        LegalEntity legalEntity = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM legal_entity WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            legalEntity = new LegalEntity(result.getInt("id"), result.getString("name"), getAddress(result.getInt("address_ref")));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return legalEntity;
    }

    public static void saveCountry(int id, String name, int code) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO country (id, name, country_code) VALUES (?, ?, ?)");
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setInt(3, code);
            System.out.println("saveCountry id=" + id + " name=" + name + " code=" + code);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void saveDistrict(Country country, String districtName) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO district (country_ref, name) VALUES (?, ?)");
            statement.setInt(1, country.getId());
            statement.setString(2, districtName);
            System.out.println("saveDistrict country=" + country.toString() + " name=" + districtName);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void saveCity(District district, String cityName, int cityCode) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO city (district_ref, name, area_code) VALUES (?, ?, ?)");
            statement.setInt(1, district.getId());
            statement.setString(2, cityName);
            statement.setInt(3, cityCode);
            System.out.println("saveCity district=" + district.toString() + " name=" + cityName + " cityCode=" + cityCode);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}