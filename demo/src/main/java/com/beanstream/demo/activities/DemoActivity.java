/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.demo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.beanstream.demo.R;
import com.beanstream.payform.activities.PayFormActivity;
import com.beanstream.payform.models.PayFormResult;
import com.beanstream.payform.models.Purchase;
import com.beanstream.payform.models.Settings;

import org.json.JSONException;
import org.json.JSONObject;

public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        final Button button = (Button) findViewById(R.id.demo_pay_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("payform.LAUNCH");
                intent.putExtra(PayFormActivity.EXTRA_PURCHASE, getPurchaseForThisDemo());
                intent.putExtra(PayFormActivity.EXTRA_SETTINGS, getSettingsForThisDemo());

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
                onPayFormError();
            }
        }
    }

    //region private helper methods
    private Purchase getPurchaseForThisDemo() {

        Purchase purchase = new Purchase(123.45, "CAD"); // Required fields: amount, currencyCode

        purchase.setCompanyName("Cabinet of Curiosities"); // default: ""
        purchase.setDescription("Item 1, Item 2, Item 3, Item 4"); // default: ""

        return purchase;
    }

    private Settings getSettingsForThisDemo() {

        Settings settings = new Settings();

        if (!((CheckBox) findViewById(R.id.demo_checkbox_color)).isChecked()) {
            settings.setColor("#aa0000"); // default: "#067aed"
        }
//        if (!((CheckBox)findViewById(R.id.demo_checkbox_font)).isChecked()) {
////        settings.setFontStyle(false); // default: true TODO
//        }
//        if (!((CheckBox)findViewById(R.id.demo_checkbox_image)).isChecked()) {
////        settings.setImage(false); // default: true TODO
//        }

        if (!((CheckBox) findViewById(R.id.demo_checkbox_billing)).isChecked()) {
            settings.setBillingAddressRequired(false); // default: true
        }
        if (!((CheckBox) findViewById(R.id.demo_checkbox_shipping)).isChecked()) {
            settings.setShippingAddressRequired(false); // default: true
        }
        if (!((CheckBox) findViewById(R.id.demo_checkbox_timeout)).isChecked()) {
            settings.setTokenRequestTimeoutInSeconds(7); // default: 6
        }

        return settings;
    }

    private void hideResults() {
        findViewById(R.id.demo_payform_error).setVisibility(View.GONE);
        findViewById(R.id.demo_payform_results).setVisibility(View.GONE);
    }

    private void onPayFormCancel() {
        hideResults();

        TextView text = (TextView) findViewById(R.id.demo_payform_error);
        text.setText(getResources().getString(R.string.demo_payform_cancelled));
        text.setVisibility(View.VISIBLE);
    }

    private void onPayFormError() {
        hideResults();

        TextView text = (TextView) findViewById(R.id.demo_payform_error);
        text.setText(getResources().getString(R.string.demo_payform_error));
        text.setVisibility(View.VISIBLE);
    }

    private void onPayFormSuccess(PayFormResult payFormResult) {
        hideResults();

        TextView text = (TextView) findViewById(R.id.demo_payform_results);
        String result = payFormResult.toString();
        try {
            result = new JSONObject(result).toString(4);
        } catch (JSONException e) {

        }
        text.setText(result);
        text.setVisibility(View.VISIBLE);
        text.setMovementMethod(new ScrollingMovementMethod());
    }
    //endregion
}
