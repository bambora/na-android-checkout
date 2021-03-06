/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.na.checkout.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dlight on 2016-08-17.
 */
public class CreditCard implements Parcelable {

    public static final Parcelable.Creator<CreditCard> CREATOR
            = new Parcelable.Creator<CreditCard>() {

        @Override
        public CreditCard createFromParcel(Parcel in) {
            return new CreditCard(in);
        }

        @Override
        public CreditCard[] newArray(int size) {
            return new CreditCard[size];
        }
    };

    private String cardNumber;
    private String cardType;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;

    public CreditCard() {
    }

    private CreditCard(Parcel parcel) {
        this.cardNumber = parcel.readString();
        this.expiryMonth = parcel.readString();
        this.expiryYear = parcel.readString();
        this.cvv = parcel.readString();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(cardNumber);
        parcel.writeString(expiryMonth);
        parcel.writeString(expiryYear);
        parcel.writeString(cvv);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCardNumber() {
        return cardNumber.replace(" ", "");
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String month) {
        this.expiryMonth = month;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String year) {
        this.expiryYear = year.substring(2);
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
