/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

/**
 * Created by dlight on 2016-08-11.
 */
public class PayForm {
    private Payment payment;
    private Address shipping;
    private Address billing;

    private boolean isBillingSameAsShipping;

    public boolean isBillingSameAsShipping() {
        return isBillingSameAsShipping;
    }

    public void setBillingSameAsShipping(boolean billingSameAsShipping) {
        isBillingSameAsShipping = billingSameAsShipping;
    }

    public PayForm() {
        isBillingSameAsShipping = false;
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
