/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.TextUtils;

import com.beanstream.payform.models.CardType;
import com.beanstream.payform.models.CreditCard;

/**
 * Created by dlight on 2016-08-17.
 */
public class CreditCardValidator {

    public static boolean isValidCard(CreditCard card) {
        if (card == null) {
            return false;
        }
        String cardNumber = card.getCardNumber();
        String cardType = card.getCardType();
        return isValidCardType(cardNumber)
                && isValidCardNumber(cardNumber, cardType)
                && isValidCvv(card.getCvv(), cardType)
                && isValidLuhn(cardNumber)
                ;
    }

    public static boolean isValidCardNumber(String cardNumber, String cardType) {
        if ((cardNumber == null) || (TextUtils.isEmpty(cardNumber.trim()))) {
            return false;
        }
        cardNumber = cardNumber.replace(" ", "");
        return CardType.getCardPatternForCardType(cardType).matcher(cardNumber).matches();
    }

    public static boolean isValidCardType(String cardNumber) {
        if ((cardNumber == null) || (TextUtils.isEmpty(cardNumber.trim()))) {
            return false;
        }
        String cardType = CardType.getCardTypeFromCardNumber(cardNumber);
        return !((CardType.INVALID).equals(cardType));
    }

    public static boolean isValidCvv(String cvv, String cardType) {
        if ((cvv == null) || (TextUtils.isEmpty(cvv.trim()))) {
            return false;
        }
        return cvv.length() == CardType.getCvvLengthForCardType(cardType);
    }

    public static boolean isValidLuhn(String cardNumber) {

        if ((cardNumber == null) || (TextUtils.isEmpty(cardNumber.trim()))) {
            return false;
        }
        cardNumber = (cardNumber + "").replace(" ", "");

        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}
