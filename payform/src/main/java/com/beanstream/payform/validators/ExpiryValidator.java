/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.beanstream.payform.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by dlight on 2016-09-06.
 */
public class ExpiryValidator implements Spinner.OnItemSelectedListener {

    private final Spinner spinner;

    public ExpiryValidator(Spinner spinner) {
        this.spinner = spinner;
    }

    public static boolean isValidExpiry(String expiry) {
        return ((expiry != null) && (!TextUtils.isEmpty(expiry))) && isNumeric(expiry);
    }

    public static ArrayList<String> expiryMonths() {
        ArrayList<String> months = new ArrayList<>();

        int january = 1;
        int december = 12;
        for (int i = january; i <= december; i++) {
            months.add(String.format(Locale.US, "%02d", i));
        }

        return months;
    }

    public static ArrayList<String> expiryYears() {
        ArrayList<String> years = new ArrayList<>();

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int maxExpiryInYears = 10;
        int maxYear = thisYear + maxExpiryInYears;
        for (int i = thisYear; i <= maxYear; i++) {
            years.add(Integer.toString(i));
        }

        return years;
    }

    private static boolean isNumeric(String str) {
        return str.matches("^\\d+$");
    }

    @Override
    public void onItemSelected(AdapterView parent, View v, int position, long id) {
        // Do nothing.
    }

    @Override
    public void onNothingSelected(AdapterView parent) {
        // Do nothing.
    }

    public boolean validate(Spinner spinner) {
        boolean result = false;
        String expiry = spinner.getSelectedItem().toString();

        TextView expiryView = (TextView) spinner.getSelectedView();
        if (isValidExpiry(expiry)) {
            result = true;
            expiryView.setError(null);
        } else {
            String error = spinner.getResources().getString(R.string.validator_prefix_invalid) + expiry;
            expiryView.setError(error);
        }
        return result;
    }
}
