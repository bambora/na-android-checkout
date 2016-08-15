/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dlight on 2016-08-11.
 */
public class PayForm implements Parcelable {
    public static final Parcelable.Creator<PayForm> CREATOR
            = new Parcelable.Creator<PayForm>() {

        @Override
        public PayForm createFromParcel(Parcel in) {
            return new PayForm(in);
        }

        @Override
        public PayForm[] newArray(int size) {
            return new PayForm[size];
        }
    };
    private Payment payment;
    private Address shipping;
    private Address billing;
    private boolean isBillingSameAsShipping;

    private PayForm(Parcel parcel) {
        payment = parcel.readParcelable(Payment.class.getClassLoader());
        shipping = parcel.readParcelable(Address.class.getClassLoader());
        billing = parcel.readParcelable(Address.class.getClassLoader());
    }

    public PayForm() {
        isBillingSameAsShipping = false;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(payment, flags);
        parcel.writeParcelable(shipping, flags);
        parcel.writeParcelable(billing, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isBillingSameAsShipping() {
        return isBillingSameAsShipping;
    }

    public void setBillingSameAsShipping(boolean billingSameAsShipping) {
        isBillingSameAsShipping = billingSameAsShipping;
    }

    public Address getShipping() {
        return shipping;
    }

    public void setShipping(Address shipping) {
        this.shipping = shipping;
    }

    public Payment getPayment() {

        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Address getBilling() {

        return billing;
    }

    public void setBilling(Address billing) {
        this.billing = billing;
    }


}
