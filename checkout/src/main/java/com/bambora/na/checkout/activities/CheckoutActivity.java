/*
 * Copyright (c) 2017 Bambora
 *
 * Created by dlight on 2016-08-04.
 */

package com.bambora.na.checkout.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bambora.na.checkout.Preferences;
import com.bambora.na.checkout.fragments.ShippingFragment;
import com.bambora.na.checkout.models.CardInfo;
import com.bambora.na.checkout.models.CheckoutResult;
import com.bambora.na.checkout.models.CreditCard;
import com.bambora.na.checkout.services.TokenService;
import com.bambora.na.checkout.validators.ViewValidator;
import com.bambora.na.checkout.R;
import com.bambora.na.checkout.fragments.BillingFragment;
import com.bambora.na.checkout.fragments.PaymentFragment;

import static android.app.Activity.RESULT_OK;

public class CheckoutActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener,
        ShippingFragment.OnBillingCheckBoxChangedListener {
    public final static String ACTION_CHECKOUT_LAUNCH = "com.bambora.na.checkout.LAUNCH";

    public final static String EXTRA_CHECKOUT_RESULT = "com.bambora.na.checkout.result";

    public final static int REQUEST_CHECKOUT = 1;

    private Button nextButton;

    // CheckoutResult
    private CheckoutResult checkoutResult;
    private CreditCard creditCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        updatePurchaseHeader(options, purchase);

        Preferences.getInstance(this.getApplicationContext()).saveData(Preferences.TOKEN_REQUEST_TIMEOUT_IN_SECONDS, String.valueOf(options.getTokenRequestTimeoutInSeconds()));

        if (savedInstanceState == null) {
            // First-time init;

            checkoutResult = new CheckoutResult();

            getFragmentManager().addOnBackStackChangedListener(this);

            if (options.isShippingAddressRequired()) {
                switchContentToShipping();
            } else if (options.isBillingAddressRequired()) {
                switchContentToBilling();
            } else {
                switchContentToPayment();
            }
        } else {
            checkoutResult = savedInstanceState.getParcelable(EXTRA_CHECKOUT_RESULT);
        }

        nextButton = (Button) findViewById(R.id.button_next);
        configureNextButton(nextButton);
    }

    @Override
    public void onBillingCheckBoxChanged(boolean isChecked) {
        checkoutResult.setIsBillingSameAsShipping(isChecked);
        updateNextButtonText();
    }

    //region Navigation
    @Override
    public void onBackStackChanged() {
        updateNextButtonText();
    }

    @Override
    public void onBackPressed() {
        hideKeyboard(this, getCurrentFocus());

        if (getFragmentManager().getBackStackEntryCount() > 1) {
            saveFragment(getCurrentFragment());

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

    private void configureNextButton(final Button button) {
        final int buttonColorDefault = getThemePrimaryColor(this);
        final int buttonColorPressed = getLighterShade(getThemePrimaryColor(this));

        button.setBackgroundColor(buttonColorDefault);
        updateNextButtonText();

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button.setBackgroundColor(buttonColorPressed);
                        break;
                    case MotionEvent.ACTION_UP:
                        button.setBackgroundColor(buttonColorDefault);
                        goToNext();
                        break;
                }
                return true;
            }
        });
    }

    private void goToNext() {
        Fragment fragment = getCurrentFragment();
        saveFragment(fragment);

        if (!ViewValidator.validateAllFields(fragment.getView())) {
            return;
        }

        if (fragment instanceof ShippingFragment) {
            if (isBillingNext()) {
                switchContentToBilling();
            } else {
                switchContentToPayment();
            }
        } else if (fragment instanceof BillingFragment) {
            switchContentToPayment();
        } else if (fragment instanceof PaymentFragment) {
            if (isInternetAvailable(getApplicationContext())) {
                startProcessing();
            } else {
                showToast(getResources().getString(R.string.error_no_connection));
            }
        }
    }

    private void showToast(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }

    private Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentById(R.id.fragment_content);
    }

    private boolean isBillingNext() {
        return (options.isBillingAddressRequired() && !(checkoutResult.isBillingSameAsShipping()));
    }

    private void saveFragment(Fragment fragment) {
        if (fragment != null) {
            if (fragment instanceof ShippingFragment) {
                checkoutResult.setShipping(((ShippingFragment) fragment).getAddress());
                checkoutResult.setIsBillingSameAsShipping(((ShippingFragment) fragment).getIsBillingSameAsShipping());

                if (checkoutResult.isBillingSameAsShipping()) {
                    checkoutResult.setBilling(((ShippingFragment) fragment).getAddress());
                }
            } else if (fragment instanceof BillingFragment) {
                checkoutResult.setBilling(((BillingFragment) fragment).getAddress());
            } else if (fragment instanceof PaymentFragment) {
                checkoutResult.setCardInfo(((PaymentFragment) fragment).getCardInfo());
                creditCard = ((PaymentFragment) fragment).getCreditCard();
            }
        }
    }
    //endregion

    //region Content Updates
    private void updateNextButtonText() {
        nextButton.setText(getTextForNextButton());
    }

    private String getTextForNextButton() {
        Fragment fragment = getCurrentFragment();

        if (fragment instanceof ShippingFragment) {
            if (isBillingNext()) {
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
                .replace(R.id.fragment_content, ShippingFragment.newInstance(checkoutResult.getShipping(), options.isBillingAddressRequired(), checkoutResult.isBillingSameAsShipping()), tag)
                .addToBackStack(tag)
                .commit();
    }

    private void switchContentToBilling() {
        String tag = BillingFragment.class.getName();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, BillingFragment.newInstance(checkoutResult.getBilling()), tag)
                .addToBackStack(tag)
                .commit();
    }

    private void switchContentToPayment() {
        String tag = PaymentFragment.class.getName();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, PaymentFragment.newInstance(checkoutResult.getCardInfo()), tag)
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

        outState.putParcelable(EXTRA_CHECKOUT_RESULT, checkoutResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ProcessingActivity.REQUEST_TOKEN) {
            if (resultCode == RESULT_OK) {
                String token = data.getStringExtra(TokenService.EXTRA_TOKEN);

                CardInfo cardInfo = checkoutResult.getCardInfo();
                cardInfo.setCode(token);
                checkoutResult.setCardInfo(cardInfo);
            }

            Intent intent = getIntent();
            intent.putExtra(EXTRA_CHECKOUT_RESULT, checkoutResult);

            setResult(resultCode, intent);

            finish();
        }
    }
    //endregion
}
