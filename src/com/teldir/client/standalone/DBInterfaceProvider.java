package com.teldir.client.standalone;

import com.teldir.core.*;

import java.sql.*;
import java.util.HashMap;

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