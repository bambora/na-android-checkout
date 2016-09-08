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

import com.beanstream.payform.Preferences;
import com.beanstream.payform.R;
import com.beanstream.payform.fragments.BillingFragment;
import com.beanstream.payform.fragments.PaymentFragment;
import com.beanstream.payform.fragments.ShippingFragment;
import com.beanstream.payform.models.CardInfo;
import com.beanstream.payform.models.CreditCard;
import com.beanstream.payform.models.PayFormResult;
import com.beanstream.payform.models.Purchase;
import com.beanstream.payform.models.Settings;
import com.beanstream.payform.services.TokenService;
import com.beanstream.payform.validators.ViewValidator;

public class PayFormActivity extends FragmentActivity implements FragmentManager.OnBackStackChangedListener,
        ShippingFragment.OnBillingCheckBoxChangedListener {

    public final static String EXTRA_PAYFORM_RESULT = "com.beanstream.payform.result";

    public final static String EXTRA_PURCHASE = "com.beanstream.payform.models.purchase";
    public final static String EXTRA_SETTINGS = "com.beanstream.payform.models.settings";
    public final static String EXTRA_SETTINGS_COLOR = "com.beanstream.payform.models.settings";

    public final static int REQUEST_PAYFORM = 1;

    private Purchase purchase;
    private Settings settings;

    // PayFormResult
    private PayFormResult payFormResult;
    private CreditCard creditCard;

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

        Preferences.getInstance(this.getApplicationContext()).saveData(Preferences.TokenRequestTimeoutInSeconds, String.valueOf(settings.getTokenRequestTimeoutInSeconds()));

        if (savedInstanceState == null) {

            // First-time init;

            payFormResult = new PayFormResult();

            getFragmentManager().addOnBackStackChangedListener(this);

            if (settings.getShippingAddressRequired()) {
                switchContentToShipping();
            } else if (settings.getBillingAddressRequired()) {
                switchContentToBilling();
            } else {
                switchContentToPayment();
            }

            updatePrimaryColor();
            updatePurchaseHeader();
        } else {
            updateBackLink();
            updateNextButton();
        }
    }

    private void updatePrimaryColor() {
        findViewById(R.id.button_next).setBackgroundColor(settings.getColor());
        findViewById(R.id.toolbar_purchase_header).setBackgroundColor(settings.getColor());
    }

    private void updatePurchaseHeader() {
        ((TextView) findViewById(R.id.header_company_name)).setText(purchase.getCompanyName());
        ((TextView) findViewById(R.id.purchase_amount)).setText(purchase.getFormattedAmount());
        ((TextView) findViewById(R.id.purchase_description)).setText(purchase.getDescription());
    }

    @Override
    public void onBillingCheckBoxChanged(boolean isChecked) {
        payFormResult.setBillingSameAsShipping(isChecked);
        updateNextButton();
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
        return (settings.getBillingAddressRequired() && !(payFormResult.isBillingSameAsShipping()));
    }

    private void goToNext() {
        String fragmentName = getCurrentFragmentName();
        Fragment fragment = getCurrentFragment();

        ViewValidator.validateAllFields(fragment.getView());
        if (!(ViewValidator.isViewValid(fragment.getView()))) {
            return;
        }

        if (fragmentName.equals(ShippingFragment.class.getName())) {
            payFormResult.setShipping(((ShippingFragment) fragment).getAddress());
            if (payFormResult.isBillingSameAsShipping()) {
                payFormResult.setBilling(((ShippingFragment) fragment).getAddress());
            }

            if (isBillingRequired()) {
                switchContentToBilling();
            } else {
                switchContentToPayment();
            }
        } else if (fragmentName.equals(BillingFragment.class.getName())) {
            payFormResult.setBilling(((BillingFragment) fragment).getAddress());

            switchContentToPayment();
        } else if (fragmentName.equals(PaymentFragment.class.getName())) {
            payFormResult.setCardInfo(((PaymentFragment) fragment).getCardInfo());
            creditCard = ((PaymentFragment) fragment).getCreditCard();

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
                .replace(R.id.fragment_content, ShippingFragment.newInstance(settings.getBillingAddressRequired(), settings.getColor()))
                .addToBackStack(ShippingFragment.class.getName())
                .commit();
    }

    private void switchContentToBilling() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, new BillingFragment().newInstance(settings.getColor()))
                .addToBackStack(BillingFragment.class.getName())
                .commit();
    }

    private void switchContentToPayment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, new PaymentFragment().newInstance(settings.getColor()))
                .addToBackStack(PaymentFragment.class.getName())
                .commit();
    }

    private void startProcessing() {
        Intent intent = new Intent(this, ProcessingActivity.class);
        intent.putExtra(TokenService.EXTRA_CREDIT_CARD, creditCard);
        intent.putExtra(EXTRA_PURCHASE, purchase);
        intent.putExtra(EXTRA_SETTINGS, settings);

        startActivityForResult(intent, ProcessingActivity.REQUEST_TOKEN);
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
