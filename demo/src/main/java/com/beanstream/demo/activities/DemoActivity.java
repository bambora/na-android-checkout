/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.demo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.beanstream.demo.R;
import com.beanstream.payform.activities.PayFormActivity;
import com.beanstream.payform.models.Purchase;
import com.beanstream.payform.models.Settings;

public class DemoActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_demo);

        final Button button = (Button) findViewById(R.id.demo_pay_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView tokenText = (TextView) findViewById(R.id.demo_pay_token);
        tokenText.setText("Token: ");
        tokenText.setVisibility(View.VISIBLE);

        Intent intent = new Intent("payform.LAUNCH");

        Purchase purchase = new Purchase(123.45, "CAD"); // Required fields: amount, currencyCode

        purchase.setCompanyName("Cabinet of Curiosities"); // default: ""
        purchase.setDescription("Item 1, Item 2, Item 3, Item 4"); // default: ""

        intent.putExtra(PayFormActivity.EXTRA_PURCHASE, purchase);

        Settings settings = new Settings();
        settings.setColor("#aa0000"); // default: "#067aed"
//        settings.setBillingAddressRequired(false); // default: true
//        settings.setShippingAddressRequired(false); // default: true
        settings.setTokenRequestTimeoutInSeconds(7); // default: 6

        intent.putExtra(PayFormActivity.EXTRA_SETTINGS, settings);

        startActivity(intent);
    }
}
