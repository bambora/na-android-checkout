/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

/**
 * Created by dlight on 2016-08-17.
 */
public class CreditCardValidator {

    public static boolean isValidCard(String cardNumber) {
        return false;
    }

    public static boolean isValidCardNumber(String cardNumber) {
        return false;
    }

    public static boolean isValidCvv(String cvv, String cardType) {
        return false;
    }

    public static boolean isValidLength(String cvv, String cardType) {
        return false;
    }

    public static boolean isValidLuhn(String cvv, String cardType) {
        return false;
    }
}
