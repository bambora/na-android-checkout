/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.payform.validators;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.TextView;

import com.bambora.payform.R;

/**
 * Created by dlight on 2016-08-17.
 */
public class EmailValidator extends TextValidator {

    public EmailValidator(TextView view) {
        super(view);
    }

    public static boolean isValidEmail(String email) {
        return ((!TextUtils.isEmpty(email)) && (Patterns.EMAIL_ADDRESS.matcher(email).matches()));
    }

    @Override
    public boolean validate(TextView view) {
        if (super.validate(view)) {
            String email = view.getText().toString();
            if (isValidEmail(email)) {
                return true;
            } else {
                String error = view.getResources().getString(R.string.validator_prefix_invalid) + " " + view.getHint();
                view.setError(error);
            }
        }
        return false;
    }
}
