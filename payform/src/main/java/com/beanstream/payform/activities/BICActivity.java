/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.models.Options;
import com.beanstream.payform.models.Purchase;

public class BICActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void updatePurchaseHeader(Options options, Purchase purchase) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_header);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findViewById(R.id.toolbar_header).setBackgroundColor(options.getColor());

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
