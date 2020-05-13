package com.teldir.core;

import java.util.ArrayList;
import java.util.Date;

public class NaturalPerson {
    private int id;
    private String firstName;
    private String familyName;
    private String patronymic;
    private String dateOfBirth;

    private Address livingAddress;

    private ArrayList<PhoneNumber> phoneNumbers;

    private Owner owner;

    public NaturalPerson(int id, String firstName, String familyName, String patronymic, String dateOfBirth, Address livingAddress, Owner owner, ArrayList<PhoneNumber> phoneNumbers) {
        this.id = id;
        this.firstName = firstName;
        this.familyName = familyName;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.livingAddress = livingAddress;
        this.owner = owner;
        this.phoneNumbers = phoneNumbers;
    }

    public String getFullName() {
        return (this.firstName + " " + this.familyName + " " + this.patronymic).replace("  ", " ");
    }

    public String[] toStringArray() {
        return new String[] { firstName + " " + familyName + " " + patronymic,
                dateOfBirth,
                livingAddress.toString(),
                getPhoneNumbersAsString()
        };
    }

    public int getId() {
        return id;
    }

    public Address getLivingAddress() {
        return livingAddress;
    }

    public void setLivingAddress(Address livingAddress) {
        this.livingAddress = livingAddress;
    }

    public ArrayList<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getPhoneNumbersAsString() {
        StringBuilder outString = new StringBuilder();
        for(int i = 0; i < phoneNumbers.size(); i++) {
            outString.append(phoneNumbers.get(i).getNumber()).append(" ");
        }
        return outString.toString();
    }

    public void setPhoneNumbers(ArrayList<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
