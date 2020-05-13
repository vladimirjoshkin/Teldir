package com.teldir.client.standalone;

import com.teldir.core.*;
import sun.swing.MenuItemLayoutHelper;

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

    public static Country getCountry(int id) {
        Country country = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM country WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            country = new Country(result.getInt("id"), result.getString("name"));
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

    public static City getCity(int id) {
        City city = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM city WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            city = new City(result.getInt("id"), getDistrict(result.getInt("district_ref")), result.getString("name"));
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

    public static void saveNaturalPerson(NaturalPerson naturalPerson) {
        
    }

    public static NaturalPerson getNaturalPerson(int id) {
        NaturalPerson naturalPerson = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM natural_person WHERE id=?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            Owner owner = getOwnerByNaturalPersonId(result.getInt("id"));
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
                phoneNumbers.add(new PhoneNumber(getHeading(result.getInt("heading_ref")), owner, result.getString("number")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return phoneNumbers;
    }

    public static void saveLegalEntity(LegalEntity legalEntity) {

    }
    /*
    public static LegalEntity getLegalEntity(int id) {
        return new LegalEntity(-1);
    }
    */
}