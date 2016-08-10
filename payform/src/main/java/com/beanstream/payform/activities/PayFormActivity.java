/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 *
 * Created by dlight on 2016-08-04.
 */

package com.beanstream.payform.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.fragments.AddressFragment;
import com.beanstream.payform.fragments.HeaderFragment;
import com.beanstream.payform.fragments.PaymentFragment;
import com.beanstream.payform.models.Purchase;
import com.beanstream.payform.models.Settings;

public class PayFormActivity extends FragmentActivity {

    public final static String EXTRA_PURCHASE = "com.beanstream.payform.models.purchase";
    public final static String EXTRA_SETTINGS = "com.beanstream.payform.models.settings";
    public final static String EXTRA_SETTINGS_COLOR = "com.beanstream.payform.models.settings.color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_form);

        Intent intent = getIntent();
        Purchase purchase = intent.getParcelableExtra(EXTRA_PURCHASE);
        Settings settings = intent.getParcelableExtra(EXTRA_SETTINGS);
        if (settings == null) {
            settings = new Settings();
        }

        if (savedInstanceState == null) {
            // First-time init;
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_header, HeaderFragment.newInstance(purchase, settings.getColor()))
                    .commit();

            if (settings.getShippingAddressRequired()) {
                SwitchContentToShipping(settings);
            } else if (settings.getBillingAddressRequired()) {
                SwitchContentToBilling(settings);
            } else {
                SwitchContentToPayment(settings);
            }
        }
    }

    //region Content Updates
    private void SwitchContentToShipping(Settings settings) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_content, AddressFragment.newInstance("Shipping"))
                .commit();

        ((TextView)findViewById(R.id.back_link)).setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.button_main)).setVisibility(View.INVISIBLE);

        // Update text
        if (settings.getBillingAddressRequired()) {
            ((TextView)findViewById(R.id.button_main)).setText(getResources().getString(R.string.main_button_to_billing));
        } else {
            ((TextView)findViewById(R.id.button_main)).setText(getResources().getString(R.string.main_button_to_payment));
        }

        // Set control visibility
        ((TextView)findViewById(R.id.button_main)).setVisibility(View.VISIBLE);
    }

    private void SwitchContentToBilling(Settings settings) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_content, AddressFragment.newInstance("Billing"))
                .commit();

        ((TextView)findViewById(R.id.back_link)).setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.button_main)).setVisibility(View.INVISIBLE);

        if (settings.getShippingAddressRequired()) {
            ((TextView)findViewById(R.id.back_link)).setText(getResources().getString(R.string.back_to_shipping));
            ((TextView)findViewById(R.id.back_link)).setVisibility(View.VISIBLE);
        }

        ((TextView)findViewById(R.id.button_main)).setText(getResources().getString(R.string.main_button_to_payment));
        ((TextView)findViewById(R.id.button_main)).setVisibility(View.VISIBLE);
    }

    private void SwitchContentToPayment(Settings settings) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_content, new PaymentFragment())
                .commit();

        ((TextView)findViewById(R.id.back_link)).setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.button_main)).setVisibility(View.INVISIBLE);

        if (settings.getBillingAddressRequired()) {
            ((TextView)findViewById(R.id.back_link)).setText(getResources().getString(R.string.back_to_billing));
            ((TextView)findViewById(R.id.back_link)).setVisibility(View.VISIBLE);
        } else if (settings.getShippingAddressRequired()) {
            ((TextView)findViewById(R.id.back_link)).setText(getResources().getString(R.string.back_to_shipping));
            ((TextView)findViewById(R.id.back_link)).setVisibility(View.VISIBLE);
        }

        ((TextView)findViewById(R.id.button_main)).setText(getResources().getString(R.string.main_button_to_process));
        ((TextView)findViewById(R.id.button_main)).setVisibility(View.VISIBLE);
    }

    private void SwitchContentToProcessing(Settings settings) {
        ((TextView)findViewById(R.id.back_link)).setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.button_main)).setVisibility(View.INVISIBLE);
    }

    //endregion
}
