/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by dlight on 2016-08-18.
 */
public class ViewValidator {

    public static boolean isViewValid(View view) {
        ArrayList<EditText> fields = getFields(view);
        for (EditText field : fields) {
            String error = field.getError() == null ? null : field.getError().toString();
            if (!TextUtils.isEmpty(error)) {
                return false;
            }
        }

        return true;
    }

    public static ArrayList<EditText> getFields(View view) {
        ArrayList<EditText> fields = new ArrayList<>();

        for (View field : view.getFocusables(View.FOCUS_FORWARD)) {
            if (field instanceof EditText) {
                fields.add((EditText) field);
            }
        }
        return fields;
    }
}
