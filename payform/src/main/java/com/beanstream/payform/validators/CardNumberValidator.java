/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.beanstream.payform.Preferences;
import com.beanstream.payform.R;
import com.beanstream.payform.models.CardType;

import java.util.ArrayList;

/**
 * Created by dlight on 2016-08-17.
 */
public class CardNumberValidator extends TextValidator {

    private EditText editText;

    public CardNumberValidator(TextView view) {
        super(view);
        this.editText = (EditText) view;
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

    private String getCleanCardNumber(String cardNumber) {
        return cardNumber.replaceAll("[^0-9]", "");
    }

    private int getIndexAfterClean(String cardNumber, int index) {
        cardNumber = cardNumber.substring(0, index);
        int offset = cardNumber.length() - getCleanCardNumber(cardNumber).length();

        return index - offset;
    }

    private void setCreditCardImage(String cardType) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, CardType.getImageForCardType(cardType), 0);
    }

    @Override
    public void afterTextChanged(Editable s) {

        editText.removeTextChangedListener(this);

        // Get text
        int index = editText.getSelectionStart();
        String cardNumber = editText.getText().toString();

        // Get card type
        String cardType = CardType.getCardTypeFromCardNumber(cardNumber);
        Preferences.getInstance(editText.getContext()).saveData(Preferences.CardType, cardType);

        setCreditCardImage(cardType);

        // Clean cardNumber
        int cleanIndex = getIndexAfterClean(cardNumber, index);
        index = cleanIndex;
        cardNumber = getCleanCardNumber(cardNumber);

        // Format cardNumber
        ArrayList<Integer> segmentLengths = CardType.getSegmentLengthsForCardType(cardType);
        Integer start = 0;
        Integer end;
        String formatted = "";
        for (Integer segmentLength : segmentLengths) {
            end = start + segmentLength;
            if (start <= cardNumber.length()) {
                if (end <= cardNumber.length()) {
                    formatted = formatted + " " + cardNumber.substring(start, end);
                } else {
                    formatted = formatted + " " + cardNumber.substring(start);
                }
                formatted = formatted.trim();
                if (end < cleanIndex) {
                    index++;
                }
            }
            start = end;
        }
        cardNumber = formatted;
        index = (index <= cardNumber.length()) ? index : cardNumber.length();

        // Set text
        editText.setText(cardNumber);
        editText.setSelection(index);

        editText.addTextChangedListener(this);
    }

    @Override
    public boolean validate(TextView view) {
        if (super.validate(view)) {
            String cardNumber = view.getText().toString();
            String cardType = CardType.getCardTypeFromCardNumber(cardNumber);

            if (isValidCardType(cardNumber)
                    && isValidCardNumber(cardNumber, cardType)
                    && isValidLuhn(cardNumber)) {
                return true;
            } else {
                String error = view.getResources().getString(R.string.validator_prefix_invalid) + " " + view.getHint();
                view.setError(error);
            }
        }
        return false;
    }
}
