/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.NumberFormat;
import java.util.Currency;

/**
 * Created by dlight on 2016-08-09.
 */
public class Purchase implements Parcelable {

    public static final String CURRENCY_CODE_CANADA = "CAD";
    public static final String CURRENCY_CODE_UNITED_STATES = "USD";

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
    private Currency currency; // required
    private String description = "";

    public Purchase(Double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    private Purchase(Parcel parcel) {
        amount = parcel.readDouble();
        currency = Currency.getInstance(parcel.readString());
        description = parcel.readString();
    }

    //region Getters and Setters
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getFormattedAmount() {
        NumberFormat format = NumberFormat.getInstance();
        format.setCurrency(currency);
        return format.getCurrency().getSymbol() + format.format(amount);
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
        parcel.writeString(currency.getCurrencyCode());
        parcel.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    //endregion
}
