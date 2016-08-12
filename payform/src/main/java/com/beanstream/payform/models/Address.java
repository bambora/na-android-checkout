/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

/**
 * Created by dlight on 2016-08-09.
 */
public class Address {
    private String name;
    private String street;
    private String postal;
    private String city;
    private String province;
    private String country;

    //region Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    //endregion

    @Override
    public String toString() {
        return new StringBuilder()
                .append("{")
                .append(" name:").append(name)
                .append(", street:").append(street)
                .append(", postal:").append(postal)
                .append(", city:").append(city)
                .append(", province:").append(province)
                .append(", country:").append(country)
                .append("}").toString();
    }
}
