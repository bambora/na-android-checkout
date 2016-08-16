/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dlight on 2016-08-16.
 */
public class TokenRequest {
    private String number;
    private String expiry_month;
    private String expiry_year;
    private String cvd;

    public String getCvd() {
        return cvd;
    }

    public void setCvd(String cvd) {
        this.cvd = cvd;
    }

    public String getExpiryYear() {

        return expiry_year;
    }

    public void setExpiryYear(String expiry_year) {
        this.expiry_year = expiry_year;
    }

    public String getExpiryMonth() {

        return expiry_month;
    }

    public void setExpiryMonth(String expiry_month) {
        this.expiry_month = expiry_month;
    }

    public String getNumber() {

        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("number", number);
            json.put("expiry_month", expiry_month);
            json.put("expiry_year", expiry_year);
            json.put("cvd", cvd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
