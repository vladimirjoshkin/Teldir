package com.teldir.core;

import java.util.ArrayList;

public class NaturalPerson {
    private String firstName;
    private String familyName;
    private String patronymic;

    private String livingAddress;

    private ArrayList<String> phoneNumbers;

    public NaturalPerson(String firstName, String familyName, String patronymic) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.patronymic = patronymic;
    }

    public NaturalPerson(String firstName, String familyName, String patronymic, String livingAddress) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.patronymic = patronymic;
        this.livingAddress = livingAddress;
    }

    public String getFullName() {
        return (this.firstName + " " + this.familyName + " " + this.patronymic).replace("  ", " ");
    }

    public String getLivingAddress() {
        return livingAddress;
    }

    public void setLivingAddress(String livingAddress) {
        this.livingAddress = livingAddress;
    }

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
