/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.demo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.beanstream.demo.R;
import com.beanstream.payform.activities.PayFormActivity;
import com.beanstream.payform.models.Options;
import com.beanstream.payform.models.PayFormResult;
import com.beanstream.payform.models.Purchase;

import org.json.JSONException;

import java.util.Currency;

public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        final Button button = (Button) findViewById(R.id.demo_pay_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startPayForm();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PayFormActivity.REQUEST_PAYFORM) {
            String error = "";
            PayFormResult result = null;

            if (resultCode == Activity.RESULT_OK) {
                result = data.getParcelableExtra(PayFormActivity.EXTRA_PAYFORM_RESULT);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                error = getResources().getString(R.string.demo_payform_cancelled);
            } else {
                error = getResources().getString(R.string.demo_payform_error);
                result = data.getParcelableExtra(PayFormActivity.EXTRA_PAYFORM_RESULT);
            }

            showError(error);
            showResults(result);
        }
    }

    private void startPayForm() {

        Options options = getOptionsForThisDemo();
        Purchase purchase = getPurchaseForThisDemo();

        Intent intent = new Intent("payform.LAUNCH");
        intent.putExtra(PayFormActivity.EXTRA_OPTIONS, options);
        intent.putExtra(PayFormActivity.EXTRA_PURCHASE, purchase);

        startActivityForResult(intent, PayFormActivity.REQUEST_PAYFORM);
    }

    private Purchase getPurchaseForThisDemo() {

        Currency currency = Currency.getInstance(Purchase.CURRENCY_CODE_CANADA);
        Purchase purchase = new Purchase(123.45, currency); // Required fields: amount, currency

        purchase.setDescription("Item 1, Item 2, Item 3, Item 4"); // default: ""

        return purchase;
    }

    private Options getOptionsForThisDemo() {

        Options options = new Options();

        if (!((CheckBox) findViewById(R.id.demo_checkbox_image)).isChecked()) {
            options.setCompanyLogoResourceId(R.drawable.custom_company_logo); // default: null
        }
        options.setCompanyName("Cabinet of Curiosities"); // default: ""

        if (!((CheckBox) findViewById(R.id.demo_checkbox_billing)).isChecked()) {
            options.setIsBillingAddressRequired(false); // default: true
        }
        if (!((CheckBox) findViewById(R.id.demo_checkbox_shipping)).isChecked()) {
            options.setIsShippingAddressRequired(false); // default: true
        }

        if (!((CheckBox) findViewById(R.id.demo_checkbox_theme)).isChecked()) {
            options.setThemeResourceId(R.style.Theme_PayFormCustom); // default: Theme.PayForm
        }

        if (!((CheckBox) findViewById(R.id.demo_checkbox_timeout)).isChecked()) {
            options.setTokenRequestTimeoutInSeconds(7); // default: 6
        }

        return options;
    }

    private void showError(String error) {
        TextView text = (TextView) findViewById(R.id.demo_payform_error);
        text.setText(error);
    }

    private void showResults(PayFormResult payFormResult) {
        String result = "";
        try {
            if (payFormResult != null) {
                result = payFormResult.toJsonObject().toString(4);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("showResults", result);

        TextView text = (TextView) findViewById(R.id.demo_payform_results);
        text.setText(result);
        text.setVisibility(View.VISIBLE);
        text.setMovementMethod(new ScrollingMovementMethod());
    }
}
