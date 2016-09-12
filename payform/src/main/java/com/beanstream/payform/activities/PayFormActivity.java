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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beanstream.payform.Preferences;
import com.beanstream.payform.R;
import com.beanstream.payform.fragments.BillingFragment;
import com.beanstream.payform.fragments.PaymentFragment;
import com.beanstream.payform.fragments.ShippingFragment;
import com.beanstream.payform.models.CardInfo;
import com.beanstream.payform.models.CreditCard;
import com.beanstream.payform.models.PayFormResult;
import com.beanstream.payform.services.TokenService;
import com.beanstream.payform.validators.ViewValidator;

public class PayFormActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener,
        ShippingFragment.OnBillingCheckBoxChangedListener {

    public final static String EXTRA_PAYFORM_RESULT = "com.beanstream.payform.result";

    public final static int REQUEST_PAYFORM = 1;

    // PayFormResult
    private PayFormResult payFormResult;
    private CreditCard creditCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_form);

        Intent intent = getIntent();
        payFormResult = intent.getParcelableExtra(EXTRA_PAYFORM_RESULT);
        if (payFormResult == null) {
            payFormResult = new PayFormResult();
        }

        final Button button = (Button) findViewById(R.id.button_next);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                goToNext();
            }
        });

        updatePurchaseHeader(options, purchase);

        Preferences.getInstance(this.getApplicationContext()).saveData(Preferences.TokenRequestTimeoutInSeconds, String.valueOf(options.getTokenRequestTimeoutInSeconds()));

        if (savedInstanceState == null) {

            // First-time init;

            getFragmentManager().addOnBackStackChangedListener(this);

            if (options.getShippingAddressRequired()) {
                switchContentToShipping();
            } else if (options.getBillingAddressRequired()) {
                switchContentToBilling();
            } else {
                switchContentToPayment();
            }

            updatePrimaryColor();
        } else {
            updateNextButton();
        }
    }

    private void updatePrimaryColor() {
        findViewById(R.id.button_next).setBackgroundColor(getThemePrimaryColor(this));
    }

    @Override
    public void onBillingCheckBoxChanged(boolean isChecked) {
        payFormResult.setBillingSameAsShipping(isChecked);
        updateNextButton();
    }

    //region Navigation
    @Override
    public void onBackStackChanged() {
        updateNextButton();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            saveCurrentFragment();
            getFragmentManager().popBackStackImmediate();
        } else {
            super.finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void goToNext() {
        saveCurrentFragment();

        Fragment fragment = getCurrentFragment();
        ViewValidator.validateAllFields(fragment.getView());
        if (!(ViewValidator.isViewValid(fragment.getView()))) {
            return;
        }

        if (fragment instanceof ShippingFragment) {
            if (isBillingRequired()) {
                switchContentToBilling();
            } else {
                switchContentToPayment();
            }
        } else if (fragment instanceof BillingFragment) {
            switchContentToPayment();
        } else if (fragment instanceof PaymentFragment) {
            startProcessing();
        }
    }

    private Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentById(R.id.fragment_content);
    }

    private boolean isBillingRequired() {
        return (options.getBillingAddressRequired() && !(payFormResult.isBillingSameAsShipping()));
    }

    private void saveCurrentFragment() {
        Fragment fragment = getCurrentFragment();
        if (fragment instanceof ShippingFragment) {
            payFormResult.setShipping(((ShippingFragment) fragment).getAddress());
            if (payFormResult.isBillingSameAsShipping()) {
                payFormResult.setBilling(((ShippingFragment) fragment).getAddress());
            }
        } else if (fragment instanceof BillingFragment) {
            payFormResult.setBilling(((BillingFragment) fragment).getAddress());
        } else if (fragment instanceof PaymentFragment) {
            payFormResult.setCardInfo(((PaymentFragment) fragment).getCardInfo());
            creditCard = ((PaymentFragment) fragment).getCreditCard();
        }
    }
    //endregion

    //region Content Updates
    private void updateNextButton() {
        TextView view = ((TextView) findViewById(R.id.button_next));
        if (view != null) {
            view.setText(getTextForNextButton());
        }
    }

    private String getTextForNextButton() {
        Fragment fragment = getCurrentFragment();

        if (fragment instanceof ShippingFragment) {
            if (isBillingRequired()) {
                return getResources().getString(R.string.next_button_to_billing);
            } else {
                return getResources().getString(R.string.next_button_to_payment);
            }
        } else if (fragment instanceof BillingFragment) {
            return getResources().getString(R.string.next_button_to_payment);
        } else {
            return getResources().getString(R.string.next_button_to_process) + " " + purchase.getFormattedAmount();
        }
    }

    private void switchContentToShipping() {
        String tag = ShippingFragment.class.getName();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, ShippingFragment.newInstance(payFormResult.getShipping(), options.getBillingAddressRequired()), tag)
                .addToBackStack(tag)
                .commit();
    }

    private void switchContentToBilling() {
        String tag = BillingFragment.class.getName();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, BillingFragment.newInstance(payFormResult.getBilling()), tag)
                .addToBackStack(tag)
                .commit();
    }

    private void switchContentToPayment() {
        String tag = PaymentFragment.class.getName();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, PaymentFragment.newInstance(payFormResult.getCardInfo()), tag)
                .addToBackStack(tag)
                .commit();
    }

    private void startProcessing() {
        Intent intent = new Intent(this, ProcessingActivity.class);
        intent.putExtra(TokenService.EXTRA_CREDIT_CARD, creditCard);
        intent.putExtra(EXTRA_PURCHASE, purchase);
        intent.putExtra(EXTRA_OPTIONS, options);

        startActivityForResult(intent, ProcessingActivity.REQUEST_TOKEN);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(EXTRA_PAYFORM_RESULT, payFormResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ProcessingActivity.REQUEST_TOKEN) {
            if (resultCode == Activity.RESULT_OK) {
                String token = data.getStringExtra(TokenService.EXTRA_TOKEN);

                CardInfo cardInfo = payFormResult.getCardInfo();
                cardInfo.setCode(token);
                payFormResult.setCardInfo(cardInfo);
            }

            Intent intent = getIntent();
            intent.putExtra(EXTRA_PAYFORM_RESULT, payFormResult);

            setResult(resultCode, intent);

            finish();
        }
    }
    //endregion
}
