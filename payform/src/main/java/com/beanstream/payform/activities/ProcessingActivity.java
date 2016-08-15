/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.beanstream.payform.R;
import com.beanstream.payform.fragments.HeaderFragment;
import com.beanstream.payform.fragments.ProcessingFragment;
import com.beanstream.payform.models.Purchase;
import com.beanstream.payform.models.Settings;

public class ProcessingActivity extends FragmentActivity {

    private Purchase purchase;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

        Intent intent = getIntent();
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
                    .replace(R.id.fragment_content, ProcessingFragment.newInstance(purchase)).commit();
        }
    }
}
