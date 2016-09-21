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

public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        final Button button = (Button) findViewById(R.id.demo_pay_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("payform.LAUNCH");
                intent.putExtra(PayFormActivity.EXTRA_OPTIONS, getOptionsForThisDemo());
                intent.putExtra(PayFormActivity.EXTRA_PURCHASE, getPurchaseForThisDemo());

                startActivityForResult(intent, PayFormActivity.REQUEST_PAYFORM);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PayFormActivity.REQUEST_PAYFORM) {

            if (resultCode == Activity.RESULT_OK) {
                PayFormResult result = data.getParcelableExtra(PayFormActivity.EXTRA_PAYFORM_RESULT);
                onPayFormSuccess(result);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                onPayFormCancel();
            } else {
                PayFormResult result = data.getParcelableExtra(PayFormActivity.EXTRA_PAYFORM_RESULT);
                onPayFormError(result);
            }
        }
    }

    //region private helper methods
    private Purchase getPurchaseForThisDemo() {

        Purchase purchase = new Purchase(123.45, "CAD"); // Required fields: amount, currencyCode

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

    private void hideError() {
        findViewById(R.id.demo_payform_error).setVisibility(View.GONE);
    }

    private void hideResults() {
        findViewById(R.id.demo_payform_results).setVisibility(View.GONE);
    }

    private void showResults(PayFormResult payFormResult) {
        TextView text = (TextView) findViewById(R.id.demo_payform_results);
        try {
            String result = payFormResult.toJsonObject().toString(4);
            Log.d("showResults", result);
            text.setText(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        text.setVisibility(View.VISIBLE);
        text.setMovementMethod(new ScrollingMovementMethod());
    }

    private void onPayFormCancel() {
        hideResults();

        TextView text = (TextView) findViewById(R.id.demo_payform_error);
        text.setText(getResources().getString(R.string.demo_payform_cancelled));
        text.setVisibility(View.VISIBLE);
    }

    private void onPayFormError(PayFormResult payFormResult) {
        showResults(payFormResult);

        TextView text = (TextView) findViewById(R.id.demo_payform_error);
        text.setText(getResources().getString(R.string.demo_payform_error));
        text.setVisibility(View.VISIBLE);
    }

    private void onPayFormSuccess(PayFormResult payFormResult) {
        hideError();
        showResults(payFormResult);
    }
    //endregion
}
