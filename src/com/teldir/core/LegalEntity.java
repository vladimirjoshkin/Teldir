package com.teldir.core;

public class LegalEntity {
    private int id;
    private String fullName;
    private String address;

    public LegalEntity(int id) {
        this.id = id;
    }

    public LegalEntity(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public LegalEntity(int id, String fullName, String address) {
        this.id = id;
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
