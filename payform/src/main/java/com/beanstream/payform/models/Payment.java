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

    public Payment() {
    }

    private Payment(Parcel parcel) {
        email = parcel.readString();
        name = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(email);
        parcel.writeString(name);
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

    //endregion

    @Override
    public String toString() {
        return new StringBuilder()
                .append("{")
                .append(" email:").append(email)
                .append(", name:").append(name)
                .append("}").toString();
    }
}
