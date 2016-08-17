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
        String cardNumber = card.getCardNumber();
        return isValidCardType(cardNumber) &&
                isValidCardNumber(cardNumber, card.getCardType()) &&
                isValidCvv(card.getCvv(), card.getCardType()) &&
                isValidLuhn(cardNumber);
    }

    public static boolean isValidCardNumber(String cardNumber, String cardType) {
        if (TextUtils.isEmpty(cardNumber)) {
            return false;
        }
        cardNumber = cardNumber.replace(" ", "");
        return CardType.getCardPatternForCardType(cardType).matcher(cardNumber).matches();
    }

    public static boolean isValidCardType(String cardNumber) {
        String cardType = CardType.getCardTypeFromCardNumber(cardNumber);
        return !((CardType.INVALID).equals(cardType));
    }

    public static boolean isValidCvv(String cvv, String cardType) {
        if (TextUtils.isEmpty(cvv)) {
            return false;
        }
        return cvv.length() == CardType.getCvvLengthForCardType(cardType);
    }

    public static boolean isValidLuhn(String cardNumber) {
        return false;
    }
}
