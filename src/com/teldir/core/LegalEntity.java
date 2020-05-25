package com.teldir.core;

import java.util.ArrayList;

public class LegalEntity {
    private int id;
    private String fullName;
    private Address address;
    private ArrayList<PhoneNumber> phoneNumbers;

    private Owner owner;

    public LegalEntity(int id, String fullName, Address address, Owner owner, ArrayList<PhoneNumber> phoneNumbers) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.owner = owner;
        this.phoneNumbers = phoneNumbers;
    }

    public LegalEntity(int id, String fullName, Address address) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
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

    public String[] getPhoneNumbersAsStringArray() {
        String[] outArr = new String[phoneNumbers.size()];
        for (int i = 0; i < phoneNumbers.size(); i++) {
            outArr[i] = phoneNumbers.get(i).getNumber();
        }
        return outArr;
    }
}
