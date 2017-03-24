/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.payform.validators;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
                    ((TextValidator) focusListener).validate(editText);
                    error = error + (editText.getError() == null ? "" : " " + editText.getError().toString());
                }
            }
        }

        for (Spinner spinner : getAllSpinnersForGroup((ViewGroup) view)) {
            Spinner.OnItemSelectedListener itemListener = spinner.getOnItemSelectedListener();
            if (itemListener != null) {
                ((ExpiryValidator) itemListener).validate(spinner);
                TextView spinnerView = (TextView) spinner.getSelectedView();
                error = error + (spinnerView.getError() == null ? "" : " " + spinnerView.getError().toString());
            }
        }

        return TextUtils.isEmpty(error);
    }

    private static List<Spinner> getAllSpinnersForGroup(ViewGroup viewGroup) {
        List<Spinner> spinners = new ArrayList<>();
        for (int i = 0, len = viewGroup.getChildCount(); i < len; i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof Spinner) {
                spinners.add((Spinner) child);
            } else if (child instanceof ViewGroup) {
                for (Spinner spinner : getAllSpinnersForGroup((ViewGroup) child)) {
                    spinners.add(spinner);
                }
            }
        }
        return spinners;
    }
}
