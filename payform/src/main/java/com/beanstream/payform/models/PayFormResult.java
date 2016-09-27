/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

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

    private CardInfo cardInfo = new CardInfo();

    private Address billing = new Address();
    private Address shipping = new Address();
    private boolean isBillingSameAsShipping = false;

    private PayFormResult(Parcel parcel) {
        cardInfo = parcel.readParcelable(CardInfo.class.getClassLoader());
        shipping = parcel.readParcelable(Address.class.getClassLoader());
        billing = parcel.readParcelable(Address.class.getClassLoader());
    }

    public PayFormResult() {
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

    public void setIsBillingSameAsShipping(boolean billingSameAsShipping) {
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


    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        try {
            if (cardInfo != null) {
                json.put("cardInfo", cardInfo.toJsonObject());
            }
            if (billing != null) {
                json.put("billingAddress", billing.toJsonObject());
            }
            if (shipping != null) {
                json.put("shippingAddress", shipping.toJsonObject());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
