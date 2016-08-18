/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.TextView;

import com.beanstream.payform.R;

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
                String name = view.getHint().toString().toUpperCase();
                String error = view.getResources().getString(R.string.validator_prefix_invalid) + " " + name;
                view.setError(error);
            }
        }
        return false;
    }
}
