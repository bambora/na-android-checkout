/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.beanstream.payform.R;
import com.beanstream.payform.fragments.HeaderFragment;
import com.beanstream.payform.fragments.ProcessingFragment;
import com.beanstream.payform.models.CreditCard;
import com.beanstream.payform.models.Purchase;
import com.beanstream.payform.models.Settings;
import com.beanstream.payform.services.TokenReceiver;
import com.beanstream.payform.services.TokenService;

public class ProcessingActivity extends FragmentActivity {

    public TokenReceiver tokenReceiver;

    private CreditCard card;
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
        card = intent.getParcelableExtra(TokenService.EXTRA_CARD);
        purchase = intent.getParcelableExtra(PayFormActivity.EXTRA_PURCHASE);
        settings = intent.getParcelableExtra(PayFormActivity.EXTRA_SETTINGS);
        if (settings == null) {
            settings = new Settings();
        }

        if (savedInstanceState == null) {
            // First-time init;
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_header, HeaderFragment.newInstance(purchase, settings.getColor())).commit();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_content, ProcessingFragment.newInstance(purchase, settings.getColor())).commit();

            setupTokenReceiver();
            startTokenService();
        }
    }

    public void startTokenService() {
        Intent intent = new Intent(this, TokenService.class);
        intent.putExtra(TokenService.EXTRA_RECEIVER, tokenReceiver);
        intent.putExtra(TokenService.EXTRA_CARD, card);
        startService(intent);
    }

    public void setupTokenReceiver() {
        tokenReceiver = new TokenReceiver(new Handler());

        tokenReceiver.setReceiver(new TokenReceiver.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == RESULT_OK) {
                    String token = resultData.getString(TokenService.EXTRA_TOKEN);

                    Intent intent = getIntent();
                    intent.putExtra(PayFormActivity.EXTRA_RESULT_TOKEN, token);
                    setResult(Activity.RESULT_OK, intent);
                }
                finish();
            }
        });
    }
}
