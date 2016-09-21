/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.models.Options;
import com.beanstream.payform.models.Purchase;

public abstract class BaseActivity extends AppCompatActivity {

    public final static String EXTRA_OPTIONS = "com.beanstream.payform.models.options";
    public final static String EXTRA_PURCHASE = "com.beanstream.payform.models.purchase";

    Options options;
    Purchase purchase;

    static int getThemePrimaryColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    static void closeKeyboard(Activity activity) {
        if (null != activity) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != imm) {
                View focus = activity.getCurrentFocus();
                if (null != focus) {
                    imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                }
            }
        }
    }

    public static void showKeyboard(Activity activity) {
        if (null != activity) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != imm) {
                View focus = activity.getCurrentFocus();
                if (null != focus) {
                    imm.showSoftInput(focus, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        options = intent.getParcelableExtra(EXTRA_OPTIONS);
        if (options == null) {
            options = new Options();
        }
        purchase = intent.getParcelableExtra(EXTRA_PURCHASE);
        if (purchase == null) {
            purchase = new Purchase(0.0, "");
        }

        if (options.getThemeResourceId() == 0) {
            super.setTheme(R.style.Theme_PayForm);
        } else {
            super.setTheme(options.getThemeResourceId());
        }
    }

    void disableHeaderBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    void updatePurchaseHeader(Options options, Purchase purchase) {
        ImageView imageView = ((ImageView) findViewById(R.id.header_company_logo));
        if (options.getCompanyLogoResourceId() == 0) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(options.getCompanyLogoResourceId());
        }

        TextView amount = ((TextView) findViewById(R.id.header_amount));
        amount.setText(purchase.getFormattedAmount());

        TextView company = ((TextView) findViewById(R.id.header_company_name));
        company.setText(options.getCompanyName());

        TextView description = ((TextView) findViewById(R.id.header_description));
        description.setText(purchase.getDescription());

        Toolbar cardHeader = (Toolbar) findViewById(R.id.toolbar_card_header);
        if (cardHeader != null) {
            cardHeader.setBackgroundColor(Color.WHITE);
            amount.setTextColor(android.graphics.Color.BLACK);
            company.setTextColor(android.graphics.Color.BLACK);
            description.setTextColor(android.graphics.Color.BLACK);
        }

        int primaryColor = getThemePrimaryColor(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_with_controls);
        toolbar.setBackgroundColor(primaryColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(primaryColor);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
