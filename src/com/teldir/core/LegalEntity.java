package com.teldir.core;

import java.util.ArrayList;

public class LegalEntity {
    private int id;
    private String fullName;
    private Address address;
    private ArrayList<PhoneNumber> phoneNumbers;

    public LegalEntity(int id, String fullName, Address address, ArrayList<PhoneNumber> phoneNumbers) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Address getAddress() {
        return address;
    }
}
