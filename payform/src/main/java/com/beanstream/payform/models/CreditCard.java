/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

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


    public final static String CARD_TYPE_AMEX = "amex";
    public final static String CARD_TYPE_DINERS = "diners";
    public final static String CARD_TYPE_DISCOVER = "discover";
    public final static String CARD_TYPE_MASTERCARD = "mastercard";
    public final static String CARD_TYPE_VISA = "visa";

    private String cardNumber;
    private String cardType;
    private String expiry;

    private int expiryMonth;
    private int expiryYear;
    private int cvd;

    private String cvv;

    public CreditCard() {
    }

    public CreditCard(String cardNumber) {
        setCardNumber(cardNumber);
    }

    public CreditCard(String cardNumber, String expiry, String cvv) {
        setCardNumber(cardNumber);
        setExpiry(expiry);
        setCvv(cvv);
    }

    private CreditCard(Parcel parcel) {
        setCardNumber(parcel.readString());
        setExpiry(parcel.readString());
        setCvv(parcel.readString());
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(cardNumber);
        parcel.writeString(expiry);
        parcel.writeString(cvv);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        this.cardType = getCardTypeFromCardNumber(cardNumber);
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
        if (expiry.length() > 0) {
            this.expiryMonth = Integer.valueOf(expiry.substring(0, 2));
            this.expiryYear = Integer.valueOf(expiry.substring(2));
        }
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public int getCvd() {
        return cvd;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
        this.cvd = TextUtils.isEmpty(cvv) ? 0 : Integer.valueOf(cvv);
    }

    private String getCardTypeFromCardNumber(String cardNumber) {
        return CARD_TYPE_VISA; //TODO set card type
    }
}
