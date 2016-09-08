/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.fragments.ProcessingFragment;
import com.beanstream.payform.models.CreditCard;
import com.beanstream.payform.models.Purchase;
import com.beanstream.payform.models.Settings;
import com.beanstream.payform.services.TokenReceiver;
import com.beanstream.payform.services.TokenService;

public class ProcessingActivity extends AppCompatActivity {

    public final static int REQUEST_TOKEN = 2;

    public TokenReceiver tokenReceiver;

    private CreditCard creditCard;
    private Purchase purchase;
    private Settings settings;


    @Override
    public void onBackPressed() {
        // Disable back button
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

        Intent intent = getIntent();
        creditCard = intent.getParcelableExtra(TokenService.EXTRA_CREDIT_CARD);
        purchase = intent.getParcelableExtra(PayFormActivity.EXTRA_PURCHASE);
        settings = intent.getParcelableExtra(PayFormActivity.EXTRA_SETTINGS);

        if (settings == null) {
            settings = new Settings();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_header);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        updatePrimaryColor();
        updatePurchaseHeader();

        if (savedInstanceState == null) {
            // First-time init;

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_content, ProcessingFragment.newInstance(purchase, settings.getColor())).commit();

            setupTokenReceiver();
            startTokenService();
        }
    }

    private void updatePrimaryColor() {
        findViewById(R.id.toolbar_header).setBackgroundColor(settings.getColor());
    }

    private void updatePurchaseHeader() {
        ((TextView) findViewById(R.id.header_company_name)).setText(purchase.getCompanyName());
        ((TextView) findViewById(R.id.purchase_amount)).setText(purchase.getFormattedAmount());
        ((TextView) findViewById(R.id.purchase_description)).setText(purchase.getDescription());
    }

    public void startTokenService() {
        Intent intent = new Intent(this, TokenService.class);
        intent.putExtra(TokenService.EXTRA_RECEIVER, tokenReceiver);
        intent.putExtra(TokenService.EXTRA_CREDIT_CARD, creditCard);
        startService(intent);
    }

    public void setupTokenReceiver() {
        tokenReceiver = new TokenReceiver(new Handler());

        tokenReceiver.setReceiver(new TokenReceiver.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                String token = "";
                if (resultCode == RESULT_OK) {
                    token = resultData.getString(TokenService.EXTRA_TOKEN);
                }

                Intent intent = getIntent();
                intent.putExtra(TokenService.EXTRA_TOKEN, token);
                setResult(resultCode, intent);

                finish();
            }
        });
    }
}
