/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.payform.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dlight on 2016-08-16.
 */
public class TokenRequest {
    private CreditCard card;

    public TokenRequest(CreditCard card) {
        this.card = card;
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("number", card.getCardNumber());
            json.put("expiry_month", card.getExpiryMonth());
            json.put("expiry_year", card.getExpiryYear());
            json.put("cvd", card.getCvv());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
