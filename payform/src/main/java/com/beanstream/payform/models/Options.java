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
public class Options implements Parcelable {

    public static final Parcelable.Creator<Options> CREATOR
            = new Parcelable.Creator<Options>() {

        @Override
        public Options createFromParcel(Parcel in) {
            return new Options(in);
        }

        @Override
        public Options[] newArray(int size) {
            return new Options[size];
        }
    };

    private int color;

    private Boolean billingAddressRequired;
    private Boolean shippingAddressRequired;

    private int tokenRequestTimeoutInSeconds;

    public Options() {
        this.color = Color.parseColor("#067aed");

        this.billingAddressRequired = true;
        this.shippingAddressRequired = true;

        this.tokenRequestTimeoutInSeconds = 6;
    }

    //region Parcelable Implementation
    private Options(Parcel parcel) {
        color = parcel.readInt();
        billingAddressRequired = (parcel.readInt() == 0);
        shippingAddressRequired = (parcel.readInt() == 0);
        tokenRequestTimeoutInSeconds = parcel.readInt();
    }
    //endregion


    //region Getters and Setters
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setColor(String colorHex) {
        this.color = Color.parseColor(colorHex);
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
    //endregion

    public int getTokenRequestTimeoutInSeconds() {
        return tokenRequestTimeoutInSeconds;
    }

    public void setTokenRequestTimeoutInSeconds(int tokenRequestTimeoutInSeconds) {
        this.tokenRequestTimeoutInSeconds = tokenRequestTimeoutInSeconds;
    }

    //region Parcelable Implementation
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
    //endregion
}
