package com.teldir.core;

import java.util.regex.Pattern;

public class PhoneNumber {
    private int id;
    private Heading heading;
    private Owner owner;
    private String number;

    /*  Number structure:
        + (C)[1-3] ( (A)[3-4] ) (B)[6-8]
        C - country code, variable length 1 - 3
        A - area code, variable length 3 - 4
        B - body, variable length 6 - 8
    */

    public PhoneNumber(int id, Heading heading, Owner owner, String number) {
        this.id = id;
        this.heading = heading;
        this.owner = owner;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public Heading getHeading() {
        return heading;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getNumber() {
        return number;
    }

    public int getCountryCode() {
        return Integer.parseInt(number.split(Pattern.quote("("))[0].split(Pattern.quote("+"))[1]);
    }

    public int getAreaCode() {
        return Integer.parseInt(number.split(Pattern.quote("("))[1].split(Pattern.quote(")"))[0]);
    }

    public int getBody() {
        System.out.println("PhoneNumber getBody number=" + number + " body=" + number.split(Pattern.quote(")"))[1]);
        return Integer.parseInt(number.split(Pattern.quote(")"))[1]);
    }

    public boolean isSame(PhoneNumber phoneNumber) {
        if(getCountryCode() == phoneNumber.getCountryCode() &&
           getAreaCode() == phoneNumber.getAreaCode() &&
           getBody() == phoneNumber.getBody()) {
            return true;
        }
        return false;
    }
}