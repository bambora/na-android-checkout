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

    private Double amount; // required
    private String currency; // required
    private String description = "";

    public Purchase(Double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }


    private Purchase(Parcel parcel) {
        amount = parcel.readDouble();
        currency = parcel.readString();
        description = parcel.readString();
    }

    //region Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //endregion

    //Parcelable
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
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
