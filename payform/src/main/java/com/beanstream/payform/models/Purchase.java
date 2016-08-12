/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.NumberFormat;

/**
 * Created by dlight on 2016-08-09.
 */
public class Purchase implements Parcelable {

    public static final Parcelable.Creator<Purchase> CREATOR
            = new Parcelable.Creator<Purchase>() {

        @Override
        public Purchase createFromParcel(Parcel in) {
            return new Purchase(in);
        }

        @Override
        public Purchase[] newArray(int size) {
            return new Purchase[size];
        }
    };
    private String companyName;
    private Double amount; // required
    private String currency; // required
    private String description;

    public Purchase(Double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Purchase(String companyName, Double amount, String currency, String description) {
        this.companyName = companyName;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }

    //region Parcelable Implementation
    private Purchase(Parcel parcel) {
        companyName = parcel.readString();
        amount = parcel.readDouble();
        currency = parcel.readString();
        description = parcel.readString();
    }

    //region Getters and Setters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getFormattedAmount() {
       return NumberFormat.getCurrencyInstance().format(amount) + " " + currency;
    }
    //endregion

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(companyName);
        parcel.writeDouble(amount);
        parcel.writeString(currency);
        parcel.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    //endregion
}
