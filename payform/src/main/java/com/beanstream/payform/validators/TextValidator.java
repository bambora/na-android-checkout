/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.beanstream.payform.R;

/**
 * Created by dlight on 2016-08-18.
 */
public class TextValidator implements TextWatcher, View.OnFocusChangeListener {

    public TextValidator(TextView view) {
        TextView textView = view;
    }

    public boolean validate(TextView view) {
        if (TextUtils.isEmpty(view.getText().toString())) {
            String error = view.getHint() + " " + view.getResources().getString(R.string.validator_suffix_empty);
            view.setError(error);
            return false;
        } else {
            view.setError(null);
            return true;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // do nothing
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // do nothing
    }

    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            validate((TextView) view);
        }
    }
}
