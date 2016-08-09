/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dlight on 2016-08-09.
 */
public class Settings implements Parcelable {

    private int color; // default: "#067aed"
    private Boolean billingAddressRequired; // default: true
    private Boolean shippingAddressRequired; // default: true
    private int tokenRequestTimeoutInSeconds; // default: 6

    public Settings() {
        this.color = Color.parseColor("#067aed");
        this.billingAddressRequired = true;
        this.shippingAddressRequired = true;
        this.tokenRequestTimeoutInSeconds = 6;
    }

    //region Getters and Setters

    public int getColor() {
        return color;
    }

    public void setColor(String colorHex) {
        this.color = Color.parseColor(colorHex);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Boolean getBillingAddressRequired() {
        return billingAddressRequired;
    }

    public void setBillingAddressRequired(Boolean billingAddressRequired) {
        this.billingAddressRequired = billingAddressRequired;
    }

    public Boolean getShippingAddressRequired() {

        return shippingAddressRequired;
    }

    public void setShippingAddressRequired(Boolean shippingAddressRequired) {
        this.shippingAddressRequired = shippingAddressRequired;
    }

    public int getTokenRequestTimeoutInSeconds() {
        return tokenRequestTimeoutInSeconds;
    }

    public void setTokenRequestTimeoutInSeconds(int tokenRequestTimeoutInSeconds) {
        this.tokenRequestTimeoutInSeconds = tokenRequestTimeoutInSeconds;
    }
    //endregion

    //region Parcelable Implementation
    private Settings(Parcel parcel) {
        color = parcel.readInt();
        billingAddressRequired = (parcel.readInt() == 0);
        shippingAddressRequired = (parcel.readInt() == 0);
        tokenRequestTimeoutInSeconds = parcel.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(color);
        parcel.writeInt(billingAddressRequired ? 0 : 1);
        parcel.writeInt(shippingAddressRequired ? 0 : 1);
        parcel.writeInt(tokenRequestTimeoutInSeconds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Settings> CREATOR
            = new Parcelable.Creator<Settings>() {

        @Override
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };
    //endregion
}
