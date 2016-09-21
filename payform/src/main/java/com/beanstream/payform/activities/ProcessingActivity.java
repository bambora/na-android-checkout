/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.models.CreditCard;
import com.beanstream.payform.services.TokenReceiver;
import com.beanstream.payform.services.TokenService;

public class ProcessingActivity extends BaseActivity {

    public final static int REQUEST_TOKEN = 2;

    private TokenReceiver tokenReceiver;

    private CreditCard creditCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

        updatePurchaseHeader(options, purchase);
        disableHeaderBackButton();

        if (savedInstanceState == null) {
            // First-time init;
            Intent intent = getIntent();
            creditCard = intent.getParcelableExtra(TokenService.EXTRA_CREDIT_CARD);
            if (creditCard == null) {
                creditCard = new CreditCard();
            }

            setupTokenReceiver();
            startTokenService();
        } else {
            creditCard = savedInstanceState.getParcelable(TokenService.EXTRA_CREDIT_CARD);
        }
    }

    @Override
    public void onBackPressed() {
        // Disable back button
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(TokenService.EXTRA_CREDIT_CARD, creditCard);
    }

    private void setupTokenReceiver() {
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

    private void startTokenService() {
        Intent intent = new Intent(this, TokenService.class);
        intent.putExtra(TokenService.EXTRA_CREDIT_CARD, creditCard);
        intent.putExtra(TokenService.EXTRA_RECEIVER, tokenReceiver);
        startService(intent);
    }

    private void updateAmount(View view) {
        TextView textView = ((TextView) view.findViewById(R.id.processing_amount));
        if (textView != null) {
            textView.setText(purchase.getFormattedAmount());
        }
    }
}
