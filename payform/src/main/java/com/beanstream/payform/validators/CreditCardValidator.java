/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.TextUtils;
import android.util.Log;

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
        return isValidCardType(cardNumber) &&
                isValidCardNumber(cardNumber, cardType) &&
                isValidCvv(card.getCvv(), cardType) &&
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
        Log.d("isValidCardType","cardNumber");
        if (TextUtils.isEmpty(cardNumber)) {
            return false;
        }
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
