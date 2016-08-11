/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 *
 * Created by dlight on 2016-08-04.
 */

package com.beanstream.payform.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.fragments.BillingFragment;
import com.beanstream.payform.fragments.HeaderFragment;
import com.beanstream.payform.fragments.PaymentFragment;
import com.beanstream.payform.fragments.ShippingFragment;
import com.beanstream.payform.models.Purchase;
import com.beanstream.payform.models.Settings;

public class PayFormActivity extends FragmentActivity implements FragmentManager.OnBackStackChangedListener {

    public final static String EXTRA_PURCHASE = "com.beanstream.payform.models.purchase";
    public final static String EXTRA_SETTINGS = "com.beanstream.payform.models.settings";
    public final static String EXTRA_SETTINGS_COLOR = "com.beanstream.payform.models.settings.color";

    private Purchase purchase;
    private Settings settings;

    @Override
    public void onBackStackChanged() {
        UpdateBackLink();
        UpdateNextButton();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_form);

        Intent intent = getIntent();
        purchase = intent.getParcelableExtra(EXTRA_PURCHASE);
        settings = intent.getParcelableExtra(EXTRA_SETTINGS);
        if (settings == null) {
            settings = new Settings();
        }

        if (savedInstanceState == null) {
            // First-time init;
            getFragmentManager().addOnBackStackChangedListener(this);
            getFragmentManager().beginTransaction().replace(R.id.fragment_header, HeaderFragment.newInstance(purchase, settings.getColor())).commit();

            if (settings.getShippingAddressRequired()) {
                SwitchContentToShipping();
            } else if (settings.getBillingAddressRequired()) {
                SwitchContentToBilling();
            } else {
                SwitchContentToPayment();
            }
        }
    }

    //region Navigation
    @Override
    public void onBackPressed() {

        goToPrevious();
    }

    public void next(View view) {

        goToNext();
    }

    public void previous(View view) {

        goToPrevious();
    }

    private void goToNext() {
        String thisFragName = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();

        if (thisFragName.equals(ShippingFragment.class.getName())) {
            SwitchContentToBilling();
        } else if (thisFragName.equals(BillingFragment.class.getName())) {
            SwitchContentToPayment();
        } else if (thisFragName.equals(PaymentFragment.class.getName())) {
//      TODO      SwitchContentToProcessing();
        }
    }

    private void goToPrevious() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStackImmediate();
        } else {
            super.finish();
        }
    }
    //endregion

    //region Content Updates
    private void UpdateBackLink() {
        ((TextView) findViewById(R.id.back_link)).setVisibility(View.INVISIBLE);

        if (getFragmentManager().getBackStackEntryCount() > 1) {
            ((TextView) findViewById(R.id.back_link)).setText(GetTextForBackLink());
            ((TextView) findViewById(R.id.back_link)).setVisibility(View.VISIBLE);
        }
    }

    private void UpdateNextButton() {
        ((TextView) findViewById(R.id.button_next)).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.button_next)).setText(GetTextForNextButton());
        ((TextView) findViewById(R.id.button_next)).setVisibility(View.VISIBLE);
    }

    private String GetTextForBackLink() {
        String backFragName = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 2).getName();

        if (backFragName.equals(BillingFragment.class.getName())) {
            return getResources().getString(R.string.back_to_billing);
        } else if (backFragName.equals(ShippingFragment.class.getName())) {
            return getResources().getString(R.string.back_to_shipping);
        } else {
            return getResources().getString(R.string.back_to_shipping);
        }
    }

    private String GetTextForNextButton() {
        String thisFragName = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();

        if (thisFragName.equals(ShippingFragment.class.getName())) {
            return getResources().getString(R.string.next_button_to_billing);
        } else if (thisFragName.equals(BillingFragment.class.getName())) {
            return getResources().getString(R.string.next_button_to_payment);
        } else {
            return getResources().getString(R.string.next_button_to_process);
        }
    }

    private void SwitchContentToShipping() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_content, new ShippingFragment()).addToBackStack(ShippingFragment.class.getName()).commit();

        // Update text
        if (settings.getBillingAddressRequired()) {
            ((TextView) findViewById(R.id.button_next)).setText(getResources().getString(R.string.next_button_to_billing));
        } else {
            ((TextView) findViewById(R.id.button_next)).setText(getResources().getString(R.string.next_button_to_payment));
        }
    }

    private void SwitchContentToBilling() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_content, new BillingFragment()).addToBackStack(BillingFragment.class.getName()).commit();

        ((TextView) findViewById(R.id.button_next)).setText(getResources().getString(R.string.next_button_to_payment));
    }

    private void SwitchContentToPayment() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_content, new PaymentFragment()).addToBackStack(PaymentFragment.class.getName()).commit();

        ((TextView) findViewById(R.id.button_next)).setText(getResources().getString(R.string.next_button_to_process));
    }

    private void SwitchContentToProcessing(Settings settings) {
        ((TextView) findViewById(R.id.back_link)).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.button_next)).setVisibility(View.INVISIBLE);
    }

    //endregion
}
