/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.models.Options;
import com.beanstream.payform.models.Purchase;

public abstract class BaseActivity extends AppCompatActivity {

    public final static String EXTRA_OPTIONS = "com.beanstream.payform.models.options";
    public final static String EXTRA_PURCHASE = "com.beanstream.payform.models.purchase";

    public Options options;
    public Purchase purchase;

    public static int getThemePrimaryColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        options = intent.getParcelableExtra(EXTRA_OPTIONS);
        if (options == null) {
            options = new Options();
        }
        purchase = intent.getParcelableExtra(EXTRA_PURCHASE);
        if (purchase == null) {
            purchase = new Purchase(0.0, "");
        }
        super.onCreate(savedInstanceState);

        if (options.getThemeResourceId() == 0) {
            super.setTheme(R.style.Theme_PayForm);
        } else {
            super.setTheme(options.getThemeResourceId());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(EXTRA_OPTIONS, options);
        outState.putParcelable(EXTRA_PURCHASE, purchase);
    }

    public void updatePurchaseHeader(Options options, Purchase purchase) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_header);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findViewById(R.id.toolbar_header).setBackgroundColor(getThemePrimaryColor(this));

        ImageView imageView = ((ImageView) findViewById(R.id.header_company_logo));
        if (options.getCompanyLogoResourceId() == 0) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(options.getCompanyLogoResourceId());
        }
        ((TextView) findViewById(R.id.header_company_name)).setText(options.getCompanyName());

        ((TextView) findViewById(R.id.header_amount)).setText(purchase.getFormattedAmount());
        ((TextView) findViewById(R.id.header_description)).setText(purchase.getDescription());
    }
}
