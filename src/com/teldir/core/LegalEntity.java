package com.teldir.core;

public class LegalEntity {
    private String fullName;
    private String address;

    public LegalEntity(String fullName) {
        this.fullName = fullName;
    }

    public LegalEntity(String fullName, String address) {
        this.fullName = fullName;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
