/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dlight on 2016-08-09.
 */
public class Payment implements Parcelable {
    public static final Parcelable.Creator<Payment> CREATOR
            = new Parcelable.Creator<Payment>() {

        @Override
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };
    private String email;
    private String name;
    private String cardNumber;
    private String expiry;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;

    public Payment() {
    }

    private Payment(Parcel parcel) {
        email = parcel.readString();
        name = parcel.readString();
        cardNumber = parcel.readString();
        expiry = parcel.readString();
        cvv = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(cardNumber);
        parcel.writeString(expiry);
        parcel.writeString(cvv);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //region Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
        if (expiry.length() > 0) {
            this.expiryMonth = expiry.substring(0, 2);
            this.expiryYear =  expiry.substring(2);
        }
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    //endregion

    @Override
    public String toString() {
        return new StringBuilder()
                .append("{")
                .append(" email:").append(email)
                .append(", name:").append(name)
                .append(", cardNumber:").append(cardNumber)
                .append(", expiry:").append(expiry)
                .append(", cvv:").append(cvv)
                .append("}").toString();
    }
}
