/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

/**
 * Created by dlight on 2016-08-09.
 */
public class Payment {
    private String email;
    private String name;
    private String cardNumber;
    private String expiry;
    private String cvv;

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    //endregion

    @Override
    public String toString() {
        return new StringBuilder()
                .append("{")
                .append(" email:").append(email)
                .append(", name:").append(name)
                .append(", cardNumber:").append(cardNumber)
                .append(", expiry:").append(expiry)
                .append(", cvv:").append(cvv)
                .append("}").toString();
    }
}
