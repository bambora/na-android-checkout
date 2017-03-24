/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.payform.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dlight on 2016-08-09.
 */
public class Address implements Parcelable {
    public static final Parcelable.Creator<Address> CREATOR
            = new Parcelable.Creator<Address>() {

        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
    private String name = "";
    private String street = "";
    private String postal = "";
    private String city = "";
    private String province = "";
    private String country = "";

    public Address() {
    }

    private Address(Parcel parcel) {
        name = parcel.readString();
        street = parcel.readString();
        postal = parcel.readString();
        city = parcel.readString();
        province = parcel.readString();
        country = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(street);
        parcel.writeString(postal);
        parcel.writeString(city);
        parcel.writeString(province);
        parcel.writeString(country);
    }

    @Override
    public int describeContents() {
        return 0;
    }


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

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("street", street);
            json.put("postal", postal);
            json.put("city", city);
            json.put("province", province);
            json.put("country", country);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
