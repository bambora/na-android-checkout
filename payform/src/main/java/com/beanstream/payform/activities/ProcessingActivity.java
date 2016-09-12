/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.fragments.ProcessingFragment;
import com.beanstream.payform.models.CreditCard;
import com.beanstream.payform.models.Options;
import com.beanstream.payform.models.Purchase;
import com.beanstream.payform.services.TokenReceiver;
import com.beanstream.payform.services.TokenService;

public class ProcessingActivity extends AppCompatActivity {

    public final static int REQUEST_TOKEN = 2;

    public TokenReceiver tokenReceiver;

    private CreditCard creditCard;

    private Options options;
    private Purchase purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

        Intent intent = getIntent();
        creditCard = intent.getParcelableExtra(TokenService.EXTRA_CREDIT_CARD);
        if (creditCard == null) {
            creditCard = new CreditCard();
        }

        options = intent.getParcelableExtra(PayFormActivity.EXTRA_OPTIONS);
        if (options == null) {
            options = new Options();
        }

        purchase = intent.getParcelableExtra(PayFormActivity.EXTRA_PURCHASE);
        if (purchase == null) {
            purchase = new Purchase();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_header);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        updatePurchaseHeader();

        if (savedInstanceState == null) {
            // First-time init;

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_content, ProcessingFragment.newInstance(options, purchase)).commit();

            setupTokenReceiver();
            startTokenService();
        }
    }

    @Override
    public void onBackPressed() {
        // Disable back button
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(PayFormActivity.EXTRA_OPTIONS, options);
        outState.putParcelable(PayFormActivity.EXTRA_PURCHASE, purchase);
    }

    private void updatePurchaseHeader() {
        findViewById(R.id.toolbar_header).setBackgroundColor(options.getColor());

        ImageView imageView = ((ImageView) findViewById(R.id.header_company_logo));
        if (purchase.getCompanyLogoResourceId() == 0) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(purchase.getCompanyLogoResourceId());
        }

        ((TextView) findViewById(R.id.header_company_name)).setText(purchase.getCompanyName());
        ((TextView) findViewById(R.id.header_amount)).setText(purchase.getFormattedAmount());
        ((TextView) findViewById(R.id.header_description)).setText(purchase.getDescription());
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

    public void startTokenService() {
        Intent intent = new Intent(this, TokenService.class);
        intent.putExtra(TokenService.EXTRA_CREDIT_CARD, creditCard);
        intent.putExtra(TokenService.EXTRA_RECEIVER, tokenReceiver);
        startService(intent);
    }
}
