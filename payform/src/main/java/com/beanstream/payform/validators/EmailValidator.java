/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by dlight on 2016-08-17.
 */
public class EmailValidator {

    public static boolean isValidEmail(String email) {
        return ((!TextUtils.isEmpty(email)) && (Patterns.EMAIL_ADDRESS.matcher(email).matches()));
    }
}
