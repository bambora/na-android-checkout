/*
 * Copyright (c) 2017 Bambora
 *
 * Created by dlight on 2016-08-04.
 */

package com.bambora.payform.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bambora.payform.models.CreditCard;
import com.bambora.payform.validators.ViewValidator;
import com.bambora.payform.Preferences;
import com.bambora.payform.R;
import com.bambora.payform.fragments.BillingFragment;
import com.bambora.payform.fragments.PaymentFragment;
import com.bambora.payform.fragments.ShippingFragment;
import com.bambora.payform.models.CardInfo;
import com.bambora.payform.models.PayFormResult;
import com.bambora.payform.services.TokenService;

public class PayFormActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener,
        ShippingFragment.OnBillingCheckBoxChangedListener {
    public final static String ACTION_PAYFORM_LAUNCH = "com.bambora.payform.LAUNCH";

    public final static String EXTRA_PAYFORM_RESULT = "com.bambora.payform.result";

    public final static int REQUEST_PAYFORM = 1;

    private Button nextButton;

    // PayFormResult
    private PayFormResult payFormResult;
    private CreditCard creditCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payform);

        updatePurchaseHeader(options, purchase);

        Preferences.getInstance(this.getApplicationContext()).saveData(Preferences.TOKEN_REQUEST_TIMEOUT_IN_SECONDS, String.valueOf(options.getTokenRequestTimeoutInSeconds()));

        if (savedInstanceState == null) {
            // First-time init;

            payFormResult = new PayFormResult();

            getFragmentManager().addOnBackStackChangedListener(this);

            if (options.isShippingAddressRequired()) {
                switchContentToShipping();
            } else if (options.isBillingAddressRequired()) {
                switchContentToBilling();
            } else {
                switchContentToPayment();
            }
        } else {
            payFormResult = savedInstanceState.getParcelable(EXTRA_PAYFORM_RESULT);
        }

        nextButton = (Button) findViewById(R.id.button_next);
        configureNextButton(nextButton);
    }

    @Override
    public void onBillingCheckBoxChanged(boolean isChecked) {
        payFormResult.setIsBillingSameAsShipping(isChecked);
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
        return (options.isBillingAddressRequired() && !(payFormResult.isBillingSameAsShipping()));
    }

    private void saveFragment(Fragment fragment) {
        if (fragment != null) {
            if (fragment instanceof ShippingFragment) {
                payFormResult.setShipping(((ShippingFragment) fragment).getAddress());
                payFormResult.setIsBillingSameAsShipping(((ShippingFragment) fragment).getIsBillingSameAsShipping());

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
                .replace(R.id.fragment_content, ShippingFragment.newInstance(payFormResult.getShipping(), options.isBillingAddressRequired(), payFormResult.isBillingSameAsShipping()), tag)
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
            if (resultCode == RESULT_OK) {
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
