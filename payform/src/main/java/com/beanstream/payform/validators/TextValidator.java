/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dlight on 2016-08-18.
 */
public class TextValidator implements TextWatcher, View.OnFocusChangeListener {
    private final TextView textView;

    public TextValidator(TextView view) {
        this.textView = view;
    }

    public void validate(TextView view) {
        if (TextUtils.isEmpty(view.getText().toString())) {
            view.setError(view.getHint() + " is required!");
        } else {
            view.setError(null);
        }
    }

    @Override
    final public void afterTextChanged(Editable s) {
        validate(textView);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // do nothing
    }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {
        // do nothing
    }

    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            validate((TextView) view);
        }
    }
}
