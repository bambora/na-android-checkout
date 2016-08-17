/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

import java.util.regex.Pattern;

/**
 * Created by dlight on 2016-08-17.
 */
public class CardType {
    public final static String INVALID = "invalid";
    public final static String AMEX = "amex";
    public final static String DINERS = "diners";
    public final static String DISCOVER = "discover";
    public final static String JCB = "jcb";
    public final static String MASTERCARD = "mastercard";
    public final static String VISA = "visa";

    public final static Pattern TYPE_PATTERN_AMEX = Pattern.compile("^3[47][0-9]{2}.*$");
    public final static Pattern TYPE_PATTERN_DINERS = Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9].*$");
    public final static Pattern TYPE_PATTERN_DISCOVER = Pattern.compile("^6(?:011|5[0-9]{2}).*$");
    public final static Pattern TYPE_PATTERN_JCB = Pattern.compile("^(?:2131|1800|35[0-9]{2}).*$");
    public final static Pattern TYPE_PATTERN_MASTERCARD = Pattern.compile("^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720).*$");
    public final static Pattern TYPE_PATTERN_VISA = Pattern.compile("^4[0-9]{3}.*$");

    public final static Pattern CARD_PATTERN_AMEX = Pattern.compile("^3[47][0-9]{13}$");
    public final static Pattern CARD_PATTERN_DINERS = Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]{11}$");
    public final static Pattern CARD_PATTERN_DISCOVER = Pattern.compile("^6(?:011|5[0-9]{2})[0-9]{12}$");
    public final static Pattern CARD_PATTERN_JCB = Pattern.compile("^(?:2131|1800|35[0-9]{3})[0-9]{11}$");
    public final static Pattern CARD_PATTERN_MASTERCARD = Pattern.compile("^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$");
    public final static Pattern CARD_PATTERN_VISA = Pattern.compile("^4[0-9]{15}$");

    public static String getCardTypeFromCardNumber(String cardNumber) {
        if (cardNumber != null) {
            cardNumber = cardNumber.replace(" ", "");
            if (TYPE_PATTERN_AMEX.matcher(cardNumber).matches()) {
                return AMEX;
            } else if (TYPE_PATTERN_DINERS.matcher(cardNumber).matches()) {
                return DINERS;
            } else if (TYPE_PATTERN_DISCOVER.matcher(cardNumber).matches()) {
                return DISCOVER;
            } else if (TYPE_PATTERN_JCB.matcher(cardNumber).matches()) {
                return JCB;
            } else if (TYPE_PATTERN_MASTERCARD.matcher(cardNumber).matches()) {
                return MASTERCARD;
            } else if (TYPE_PATTERN_VISA.matcher(cardNumber).matches()) {
                return VISA;
            }
        }
        return INVALID;
    }

    public static Pattern getCardPatternForCardType(String cardType) {
        if (cardType != null) {
            cardType = cardType.replace(" ", "");
            if (AMEX.equals(cardType)) {
                return CARD_PATTERN_AMEX;
            } else if (DINERS.equals(cardType)) {
                return CARD_PATTERN_DINERS;
            } else if (DISCOVER.equals(cardType)) {
                return CARD_PATTERN_DISCOVER;
            } else if (JCB.equals(cardType)) {
                return CARD_PATTERN_JCB;
            } else if (MASTERCARD.equals(cardType)) {
                return CARD_PATTERN_MASTERCARD;
            } else if (VISA.equals(cardType)) {
                return CARD_PATTERN_VISA;
            }
        }
        return Pattern.compile(INVALID);
    }

    public static int getCvvLengthForCardType(String cardType) {
        if (AMEX.equals(cardType)) {
            return 4;
        } else {
            return 3;
        }
    }
}
