/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.payform.validators;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bambora.payform.Preferences;
import com.bambora.payform.models.CardType;
import com.bambora.payform.R;

import java.util.ArrayList;

/**
 * Created by dlight on 2016-08-17.
 */
public class CardNumberValidator extends TextValidator {

    private final EditText editText;
    private final ImageView imageView;

    private int selection;

    public CardNumberValidator(TextView view, ImageView imageView) {
        super(view);

        this.editText = (EditText) view;
        this.imageView = imageView;

        String cardType = refreshSavedCardType();
        swapCreditCardImage(cardType);
        toggleCreditCardImage();
    }

    public static boolean isValidCardNumber(String cardNumber, String cardType) {
        if ((cardNumber == null) || (TextUtils.isEmpty(cardNumber.trim()))) {
            return false;
        }
        cardNumber = cardNumber.replace(" ", "");
        return CardType.getCardPattern(cardType).matcher(cardNumber).matches();
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

    private String refreshSavedCardType() {
        Preferences preferences = Preferences.getInstance(editText.getContext());
        String cardType = CardType.getCardTypeFromCardNumber(editText.getText().toString());
        preferences.saveData(Preferences.CARD_TYPE, cardType);

        return cardType;
    }

    private String getCleanCardNumber(String cardNumber) {
        return cardNumber.replaceAll("[^0-9]", "");
    }

    private void getSelectionAfterClean(String cardNumber) {
        cardNumber = cardNumber.substring(0, selection);
        int offset = cardNumber.length() - getCleanCardNumber(cardNumber).length();

        selection = selection - offset;
    }

    private void swapCreditCardImage(String cardType) {
        if (imageView != null) {
            imageView.setImageResource(CardType.getImageResource(cardType));
        }
    }

    private void toggleCreditCardImage() {

        if (TextUtils.isEmpty(editText.getError())) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    private void updateCreditCardType(String cardType) {
        Preferences preferences = Preferences.getInstance(editText.getContext());
        String savedCardType = preferences.getData(Preferences.CARD_TYPE);

        if (!cardType.equals(savedCardType)) {
            preferences.saveData(Preferences.CARD_TYPE, cardType);
            swapCreditCardImage(cardType);
            setEditTextMaxLength(editText, CardType.getLengthOfFormattedCardNumber(cardType));
        }
        toggleCreditCardImage();
    }

    private String formatCardNumberField(String cardNumber, String cardType) {
        // Clean cardNumber
        int cleanIndex = selection;
        getSelectionAfterClean(cardNumber);
        cardNumber = getCleanCardNumber(cardNumber);

        // Format cardNumber
        ArrayList<Integer> segmentLengths = CardType.getSegmentLengths(cardType);
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
                if (end < cleanIndex) {
                    selection++;
                }
            }
            start = end;
        }
        formatted = formatted.trim();
        selection = (selection <= formatted.length()) ? selection : formatted.length();

        return formatted;
    }

    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);
        editText.setError(null);
        selection = editText.getSelectionStart();

        String cardNumber = s.toString();
        String cardType = CardType.getCardTypeFromCardNumber(cardNumber);
        updateCreditCardType(cardType);

        cardNumber = formatCardNumberField(cardNumber, cardType);

        // Set text
        s.clear();
        s.append(cardNumber);

        editText.setSelection(selection);
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
                view.setError(null);
            } else {
                String error = view.getResources().getString(R.string.validator_prefix_invalid) + " " + view.getHint();
                view.setError(error);
            }
        }
        toggleCreditCardImage();

        return TextUtils.isEmpty(view.getError());
    }
}
