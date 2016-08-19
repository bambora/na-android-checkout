/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.TextUtils;
import android.widget.TextView;

import com.beanstream.payform.Preferences;
import com.beanstream.payform.R;
import com.beanstream.payform.models.CardType;

/**
 * Created by dlight on 2016-08-17.
 */
public class CvvValidator extends TextValidator {

    public CvvValidator(TextView view) {
        super(view);
    }

    public static boolean isValidCvv(String cvv, String cardType) {
        if ((cvv == null) || (TextUtils.isEmpty(cvv.trim()))) {
            return false;
        }
        return cvv.length() == CardType.getCvvLengthForCardType(cardType);
    }

    @Override
    public boolean validate(TextView view) {
        boolean result = false;
        if (super.validate(view)) {
            String cvv = view.getText().toString();
            String cardType = Preferences.getInstance(view.getContext()).getData(Preferences.CardType);

            if (isValidCvv(cvv, cardType)) {
                result = true;
            } else {
                if (cvv.length() > CardType.CVV_MAX_LENGTH) {
                    view.setText(cvv.substring(0, CardType.CVV_MAX_LENGTH));
                }
                String name = view.getHint().toString().toUpperCase();
                String error = view.getResources().getString(R.string.validator_prefix_invalid) + " " + name;
                view.setError(error);
            }
        }
        return result;
    }
}
