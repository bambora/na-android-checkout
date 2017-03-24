/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.payform.models;

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

    private int companyLogoResourceId = 0;
    private String companyName = "";

    private Boolean isBillingAddressRequired = true;
    private Boolean isShippingAddressRequired = true;

    private int themeResourceId = 0;

    private int tokenRequestTimeoutInSeconds = 6;

    public Options() {
    }

    //region Parcelable Implementation
    private Options(Parcel parcel) {
        companyLogoResourceId = parcel.readInt();
        companyName = parcel.readString();

        isBillingAddressRequired = (parcel.readInt() == 0);
        isShippingAddressRequired = (parcel.readInt() == 0);

        themeResourceId = parcel.readInt();

        tokenRequestTimeoutInSeconds = parcel.readInt();
    }
    //endregion

    //region Getters and Setters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyLogoResourceId() {
        return companyLogoResourceId;
    }

    public void setCompanyLogoResourceId(int resourceId) {
        this.companyLogoResourceId = resourceId;
    }

    public Boolean isBillingAddressRequired() {
        return isBillingAddressRequired;
    }

    public void setIsBillingAddressRequired(Boolean isBillingAddressRequired) {
        this.isBillingAddressRequired = isBillingAddressRequired;
    }

    public Boolean isShippingAddressRequired() {

        return isShippingAddressRequired;
    }

    public void setIsShippingAddressRequired(Boolean isShippingAddressRequired) {
        this.isShippingAddressRequired = isShippingAddressRequired;
    }

    public int getThemeResourceId() {
        return themeResourceId;
    }

    public void setThemeResourceId(int resourceId) {
        this.themeResourceId = resourceId;
    }

    public int getTokenRequestTimeoutInSeconds() {
        return tokenRequestTimeoutInSeconds;
    }

    public void setTokenRequestTimeoutInSeconds(int tokenRequestTimeoutInSeconds) {
        this.tokenRequestTimeoutInSeconds = tokenRequestTimeoutInSeconds;
    }
    //endregion

    //region Parcelable Implementation
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(companyLogoResourceId);
        parcel.writeString(companyName);

        parcel.writeInt(isBillingAddressRequired ? 0 : 1);
        parcel.writeInt(isShippingAddressRequired ? 0 : 1);

        parcel.writeInt(themeResourceId);

        parcel.writeInt(tokenRequestTimeoutInSeconds);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    //endregion
}
