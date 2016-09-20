/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by dlight on 2016-08-18.
 */
public class ViewValidator {
    public static boolean validateAllFields(View view) {
        String error = "";

        for (View field : view.getFocusables(View.FOCUS_FORWARD)) {
            View.OnFocusChangeListener focusListener = field.getOnFocusChangeListener();
            if (focusListener != null) {
                if (field instanceof EditText) {
                    EditText editText = (EditText) field;
                    ((TextValidator) editText.getOnFocusChangeListener()).validate(editText);
                    error = error + (editText.getError() == null ? null : editText.getError().toString());
                } else if (field instanceof Spinner) {
                    Spinner spinner = (Spinner) field;
                    ((ExpiryValidator) spinner.getOnFocusChangeListener()).validate(spinner);
                    TextView spinnerView = (TextView) spinner.getSelectedView();
                    error = error + (spinnerView.getError() == null ? null : spinnerView.getError().toString());
                }
            }
        }

        return TextUtils.isEmpty(error);
    }
}
