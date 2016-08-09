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
        TextView tokenText = (TextView)findViewById(R.id.demo_pay_token);
        tokenText.setText("Token: ");
        tokenText.setVisibility(View.VISIBLE);

        Intent intent = new Intent("payform.LAUNCH");

        intent.putExtra(PayFormActivity.EXTRA_COMPANY_NAME, "Cabinet of Curiosities");

        intent.putExtra(PayFormActivity.EXTRA_PURCHASE_AMOUNT, 123.00);
        intent.putExtra(PayFormActivity.EXTRA_PURCHASE_CURRENCY_CODE, "CAD");
        intent.putExtra(PayFormActivity.EXTRA_PURCHASE_DESCRIPTION, "Item 1, Item 2, Item 3, Item 4");

        intent.putExtra(PayFormActivity.EXTRA_PRIMARY_COLOR, "#aa0000"); // default: "#067aed"
        intent.putExtra(PayFormActivity.EXTRA_BILLING_ADDRESS_REQUIRED, false); // default: true
        intent.putExtra(PayFormActivity.EXTRA_SHIPPING_ADDRESS_REQUIRED, false); // default: true
        intent.putExtra(PayFormActivity.EXTRA_TOKEN_REQUEST_TIMEOUT_SECONDS, 77); // default: 6

        startActivity(intent);
    }
}
