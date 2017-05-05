/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.na.checkout.models;

import com.bambora.na.checkout.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by dlight on 2016-08-17.
 */
public class CardType {
    public final static String INVALID = "invalid";
    public final static String AMEX = "amex";
    public final static String DINERS = "diners";
    public final static String DISCOVER = "discover";
    private final static String JCB = "jcb";
    public final static String MASTERCARD = "mastercard";
    public final static String VISA = "visa";

    private final static Pattern TYPE_PATTERN_AMEX = Pattern.compile("^3[47][0-9]{2}.*$");
    private final static Pattern TYPE_PATTERN_DINERS = Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9].*$");
    private final static Pattern TYPE_PATTERN_DISCOVER = Pattern.compile("^6(?:011|5[0-9]{2}).*$");
    private final static Pattern TYPE_PATTERN_JCB = Pattern.compile("^(?:2131|1800|35[0-9]{2}).*$");
    private final static Pattern TYPE_PATTERN_MASTERCARD = Pattern.compile("^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720).*$");
    private final static Pattern TYPE_PATTERN_VISA = Pattern.compile("^4[0-9]{3}.*$");

    private final static Pattern CARD_PATTERN_AMEX = Pattern.compile("^3[47][0-9]{13}$");
    private final static Pattern CARD_PATTERN_DINERS = Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]{11}$");
    private final static Pattern CARD_PATTERN_DISCOVER = Pattern.compile("^6(?:011|5[0-9]{2})[0-9]{12}$");
    private final static Pattern CARD_PATTERN_JCB = Pattern.compile("^(?:2131|1800|35[0-9]{3})[0-9]{11}$");
    private final static Pattern CARD_PATTERN_MASTERCARD = Pattern.compile("^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$");
    private final static Pattern CARD_PATTERN_VISA = Pattern.compile("^4[0-9]{15}$");

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

    public static int getLengthOfFormattedCardNumber(String cardType) {
        int length = 0;
        if (cardType != null) {
            cardType = cardType.replace(" ", "");
            if (AMEX.equals(cardType)) {
                // 4-6-5
                length = 17;
            } else if (DINERS.equals(cardType)) {
                // 4-6-4
                length = 14;
            } else {
                // 4-4-4-4
                length = 19;
            }
        }
        return length;
    }

    public static ArrayList<Integer> getSegmentLengths(String cardType) {
        ArrayList<Integer> format = new ArrayList<>();
        if (cardType != null) {
            cardType = cardType.replace(" ", "");
            if (AMEX.equals(cardType)) {
                // 4-6-5
                format.add(4);
                format.add(6);
                format.add(5);
            } else if (DINERS.equals(cardType)) {
                // 4-6-4
                format.add(4);
                format.add(6);
                format.add(4);
            } else {
                // 4-4-4-4
                format.add(4);
                format.add(4);
                format.add(4);
                format.add(4);
            }
        }
        return format;
    }

    public static Pattern getCardPattern(String cardType) {
        if (cardType != null) {
            cardType = cardType.replace(" ", "");
            switch (cardType) {
                case AMEX:
                    return CARD_PATTERN_AMEX;
                case DINERS:
                    return CARD_PATTERN_DINERS;
                case DISCOVER:
                    return CARD_PATTERN_DISCOVER;
                case JCB:
                    return CARD_PATTERN_JCB;
                case MASTERCARD:
                    return CARD_PATTERN_MASTERCARD;
                case VISA:
                    return CARD_PATTERN_VISA;
            }
        }
        return Pattern.compile(INVALID);
    }

    public static int getCvvLength(String cardType) {
        if (AMEX.equals(cardType)) {
            return 4;
        } else {
            return 3;
        }
    }

    public static int getImageResource(String cardType) {
        if (cardType != null) {
            cardType = cardType.replace(" ", "");
            if (AMEX.equals(cardType)) {
                return R.drawable.amex;
            } else if (DINERS.equals(cardType)) {
                return R.drawable.dinersclub;
            } else if (DISCOVER.equals(cardType)) {
                return R.drawable.discover;
            } else if (JCB.equals(cardType)) {
                return R.drawable.jcb;
            } else if (MASTERCARD.equals(cardType)) {
                return R.drawable.mastercard;
            } else if (VISA.equals(cardType)) {
                return R.drawable.visa;
            }
        }
        return R.drawable.ic_credit_card_black;
    }
}
