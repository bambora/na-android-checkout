/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.beanstream.payform.R;

import java.util.Calendar;

/**
 * Created by dlight on 2016-09-06.
 */
public class ExpiryValidator extends TextValidator {

    public static int EXPIRY_LENGTH = 5; // MM/YY
    public static int EXPIRY_DELIMITER_POSITION = 2;
    public static String EXPIRY_DELIMITER = "/";

    private EditText editText;

    public ExpiryValidator(TextView view) {
        super(view);
        this.editText = (EditText) view;
    }

    public static boolean isValidExpiry(String expiry) {
        if ((expiry == null) || (TextUtils.isEmpty(expiry)) || (expiry.length() != EXPIRY_LENGTH)) {
            return false;
        }

        return isValidMonth(expiry) && isValidYear(expiry);
    }

    public static String formatExpiry(String expiry) {
        if ((expiry == null) || (TextUtils.isEmpty(cleanExpiry(expiry)))) {
            expiry = "";
        } else {
            expiry = cleanExpiry(expiry);
            expiry = delimitExpiry(expiry);
            expiry = resizeExpiry(expiry);
        }
        return expiry;
    }

    private static boolean isValidMonth(String expiry) {
        int month = Integer.parseInt(expiry.split(EXPIRY_DELIMITER)[0]);
        return (month >= 1 && month <= 12);
    }

    private static boolean isValidYear(String expiry) {
        int year = Integer.parseInt(expiry.split(EXPIRY_DELIMITER)[1]);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100;
        return (year >= currentYear);
    }

    private static String cleanExpiry(String expiry) {
        return expiry.replaceAll("[^0-9]", "");
    }

    private static String delimitExpiry(String expiry) {
        if (expiry.length() > EXPIRY_DELIMITER_POSITION) {
            String month = expiry.substring(0, EXPIRY_DELIMITER_POSITION);
            String year = expiry.substring(EXPIRY_DELIMITER_POSITION);
            expiry = month + EXPIRY_DELIMITER + year;

        } else if (expiry.length() == EXPIRY_DELIMITER_POSITION) {
            expiry = expiry + EXPIRY_DELIMITER;
        }
        return expiry;
    }

    private static String resizeExpiry(String expiry) {
        if (expiry.length() > EXPIRY_LENGTH) {
            expiry = expiry.substring(0, EXPIRY_LENGTH);
        }
        return expiry;
    }

    @Override
    public void afterTextChanged(Editable s) {

        editText.removeTextChangedListener(this);

        // Get text
        int index = editText.getSelectionStart();
        String expiry = editText.getText().toString();

        // Format expiry
        expiry = formatExpiry(expiry);

        // Reset index
        if (index == EXPIRY_DELIMITER_POSITION) {
            index = index + 1;
        }
        if (index >= expiry.length()) {
            expiry.length();
        }

        // Set text
        editText.setText(expiry);
        editText.setSelection(index);

        editText.addTextChangedListener(this);
    }

    @Override
    public boolean validate(TextView view) {
        boolean result = false;
        if (super.validate(view)) {
            String expiry = view.getText().toString();

            if (isValidExpiry(expiry)) {
                result = true;
            } else {
                String error = view.getResources().getString(R.string.validator_prefix_invalid) + " " + view.getHint();
                view.setError(error);
            }
        }
        return result;
    }
}
