package com.teldir.core;

public class PhoneNumber {
    private Heading heading;
    private Owner owner;
    private String number;

    public PhoneNumber(Heading heading, Owner owner, String number) {
        this.heading = heading;
        this.owner = owner;
        this.number = number;
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
}