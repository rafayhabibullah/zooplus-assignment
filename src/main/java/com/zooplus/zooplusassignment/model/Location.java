package com.zooplus.zooplusassignment.model;

public class Location {
    private String currency;
    private String countryCode;

    public Location() {
    }

    public Location(String currency, String countryCode) {
        this.currency = currency;
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isEmpty() {
        return this.getCurrency() == null || this.getCountryCode() == null;
    }
}
