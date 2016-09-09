/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dlight on 2016-08-11.
 */
public class PayFormResult implements Parcelable {
    public static final Parcelable.Creator<PayFormResult> CREATOR
            = new Parcelable.Creator<PayFormResult>() {

        @Override
        public PayFormResult createFromParcel(Parcel in) {
            return new PayFormResult(in);
        }

        @Override
        public PayFormResult[] newArray(int size) {
            return new PayFormResult[size];
        }
    };

    private CardInfo cardInfo;
    private Address shipping;
    private Address billing;

    private boolean isBillingSameAsShipping;

    private PayFormResult(Parcel parcel) {
        cardInfo = parcel.readParcelable(CardInfo.class.getClassLoader());
        shipping = parcel.readParcelable(Address.class.getClassLoader());
        billing = parcel.readParcelable(Address.class.getClassLoader());
    }

    public PayFormResult() {
        cardInfo = new CardInfo();

        isBillingSameAsShipping = false;

        billing = new Address();
        shipping = new Address();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(cardInfo, flags);
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

    public CardInfo getCardInfo() {

        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public Address getBilling() {

        return billing;
    }

    public void setBilling(Address billing) {
        this.billing = billing;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (cardInfo != null) {
            sb.append("cardInfo:").append(cardInfo.toString());
        }
        if (billing != null) {
            if (cardInfo != null) {
                sb.append(",");
            }
            sb.append("billingAddress:").append(billing.toString());
        }
        if (shipping != null) {
            if ((cardInfo != null) || (billing != null)) {
                sb.append(",");
            }
            sb.append("shippingAddress:").append(shipping.toString());
        }
        sb.append("}");
        return sb.toString();
    }

}
