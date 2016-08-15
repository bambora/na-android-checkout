/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 *
 * Created by dlight on 2016-08-04.
 */

package com.beanstream.payform.activities;

import android.app.Activity;
import android.app.Fragment;
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
import com.beanstream.payform.models.PayForm;
import com.beanstream.payform.models.Purchase;
import com.beanstream.payform.models.Settings;

public class PayFormActivity extends FragmentActivity implements FragmentManager.OnBackStackChangedListener,
        ShippingFragment.OnBillingCheckBoxChangedListener {

    public final static String EXTRA_PURCHASE = "com.beanstream.payform.models.purchase";
    public final static String EXTRA_SETTINGS = "com.beanstream.payform.models.settings";
    public final static String EXTRA_PAYFORM = "com.beanstream.payform.models.payform";
    public final static String EXTRA_RESULT_TOKEN = "com.beanstream.payform.result.token";

    public final static int REQUEST_PAYFORM_TOKEN = 1;

    private Purchase purchase;
    private Settings settings;

    // PayForm
    private PayForm payform;

    @Override
    public void onBackStackChanged() {
        updateBackLink();
        updateNextButton();
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

            payform = new PayForm();

            // First-time init;
            getFragmentManager().addOnBackStackChangedListener(this);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_header, HeaderFragment.newInstance(purchase, settings.getColor())).commit();

            if (settings.getShippingAddressRequired()) {
                switchContentToShipping();
            } else if (settings.getBillingAddressRequired()) {
                switchContentToBilling();
            } else {
                switchContentToPayment();
            }
        }
    }

    @Override
    public void onBillingCheckBoxChanged(boolean isChecked) {
        payform.setBillingSameAsShipping(isChecked);
        updateNextButton();
    }

    @Override
    public void finish() {
        Intent intent = getIntent();
        intent.putExtra(PayFormActivity.EXTRA_RESULT_TOKEN, "TODO-TOKEN-TODO");
        setResult(Activity.RESULT_OK, intent);

        super.finish();
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

    private boolean isBillingRequired() {
        return (settings.getBillingAddressRequired() && !(payform.isBillingSameAsShipping()));
    }

    private void goToNext() {
        String fragmentName = getCurrentFragmentName();
        Fragment fragment = getCurrentFragment();

        if (fragmentName.equals(ShippingFragment.class.getName())) {
            payform.setShipping(((ShippingFragment) fragment).getAddress());

            if (isBillingRequired()) {
                switchContentToBilling();
            } else {
                switchContentToPayment();
            }
        } else if (fragmentName.equals(BillingFragment.class.getName())) {
            payform.setBilling(((BillingFragment) fragment).getAddress());

            switchContentToPayment();
        } else if (fragmentName.equals(PaymentFragment.class.getName())) {
            payform.setPayment(((PaymentFragment) fragment).getPayment());

            startProcessing();
        }
    }

    private Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentById(R.id.fragment_content);
    }

    private String getCurrentFragmentName() {
        return getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();
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
    private void updateBackLink() {
        TextView view = ((TextView) findViewById(R.id.back_link));
        if (view != null) {
            view.setVisibility(View.GONE);
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                view.setText(getTextForBackLink());
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    private void updateNextButton() {
        TextView view = ((TextView) findViewById(R.id.button_next));
        if (view != null) {
            view.setText(getTextForNextButton());
        }
    }

    private String getTextForBackLink() {
        String backFragName = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 2).getName();

        if (backFragName.equals(BillingFragment.class.getName())) {
            return getResources().getString(R.string.back_to_billing);
        } else if (backFragName.equals(ShippingFragment.class.getName())) {
            return getResources().getString(R.string.back_to_shipping);
        } else {
            return getResources().getString(R.string.back_to_shipping);
        }
    }

    private String getTextForNextButton() {
        String thisFragName = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();

        if (thisFragName.equals(ShippingFragment.class.getName())) {
            if (isBillingRequired()) {
                return getResources().getString(R.string.next_button_to_billing);
            } else {
                return getResources().getString(R.string.next_button_to_payment);
            }
        } else if (thisFragName.equals(BillingFragment.class.getName())) {
            return getResources().getString(R.string.next_button_to_payment);
        } else {
            return getResources().getString(R.string.next_button_to_process) + " " + purchase.getFormattedAmount();
        }
    }

    private void switchContentToShipping() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, ShippingFragment.newInstance(settings.getBillingAddressRequired()))
                .addToBackStack(ShippingFragment.class.getName())
                .commit();
    }

    private void switchContentToBilling() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, new BillingFragment())
                .addToBackStack(BillingFragment.class.getName())
                .commit();
    }

    private void switchContentToPayment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, new PaymentFragment())
                .addToBackStack(PaymentFragment.class.getName())
                .commit();
    }

    private void startProcessing() {
        Intent intent = new Intent(this, ProcessingActivity.class);
        intent.putExtra(EXTRA_PAYFORM, payform);
        intent.putExtra(EXTRA_PURCHASE, purchase);
        intent.putExtra(EXTRA_SETTINGS, settings);

        startActivity(intent);
    }

    //endregion
}
