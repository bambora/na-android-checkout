/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 *
 * Created by dlight on 2016-08-04.
 */

package com.beanstream.payform.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.beanstream.payform.R;
import com.beanstream.payform.fragments.HeaderFragment;

public class PayFormActivity extends FragmentActivity {
    public final static String EXTRA_COMPANY_LOGO = "com.beanstream.payform.company.logo"; // not required
    public final static String EXTRA_COMPANY_NAME = "com.beanstream.payform.company.name"; // not required

    public final static String EXTRA_PURCHASE_AMOUNT = "com.beanstream.payform.purchase.amount";
    public final static String EXTRA_PURCHASE_CURRENCY_CODE = "com.beanstream.payform.purchase.currencycode";
    public final static String EXTRA_PURCHASE_DESCRIPTION = "com.beanstream.payform.purchase.description"; // not required

    public final static String EXTRA_PRIMARY_COLOR = "com.beanstream.payform.primarycolor"; // default: "#067aed"
    public final static String EXTRA_BILLING_ADDRESS_REQUIRED = "com.beanstream.payform.billingrequired"; // default: true
    public final static String EXTRA_SHIPPING_ADDRESS_REQUIRED = "com.beanstream.payform.shippingrequired"; // default: true
    public final static String EXTRA_TOKEN_REQUEST_TIMEOUT_SECONDS = "com.beanstream.payform.tokentimeoutseconds"; // default:6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_form);

        Intent intent = getIntent();

        String name = intent.getStringExtra(EXTRA_COMPANY_NAME);

        Double amount = intent.getDoubleExtra(EXTRA_PURCHASE_AMOUNT, 0.0);
        String currency = intent.getStringExtra(EXTRA_PURCHASE_CURRENCY_CODE);
        String description = intent.getStringExtra(EXTRA_PURCHASE_DESCRIPTION);

        String color = intent.getStringExtra(EXTRA_PRIMARY_COLOR);

        Boolean shippingAddressRequired = intent.getBooleanExtra(EXTRA_SHIPPING_ADDRESS_REQUIRED, true);
        Boolean billingAddressRequired = intent.getBooleanExtra(EXTRA_BILLING_ADDRESS_REQUIRED, true);
        int tokenRequestTimeoutInSeconds = intent.getIntExtra(EXTRA_TOKEN_REQUEST_TIMEOUT_SECONDS, 6);

        if (savedInstanceState == null) {
            // First-time init; create fragment to embed in activity.
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_header, HeaderFragment.newInstance(amount, currency, description, name, color))
//                    .addToBackStack(null)
                    .commit();
        }
    }
}
