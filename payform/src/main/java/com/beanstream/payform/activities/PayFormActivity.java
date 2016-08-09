/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 *
 * Created by dlight on 2016-08-04.
 */

package com.beanstream.payform.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.beanstream.payform.R;
import com.beanstream.payform.fragments.HeaderFragment;
import com.beanstream.payform.models.Purchase;
import com.beanstream.payform.models.Settings;

public class PayFormActivity extends FragmentActivity {
    public final static String EXTRA_PURCHASE = "com.beanstream.payform.models.purchase";
    public final static String EXTRA_SETTINGS = "com.beanstream.payform.models.settings";
    public final static String EXTRA_SETTINGS_COLOR = "com.beanstream.payform.models.settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_form);

        Intent intent = getIntent();
        Purchase purchase = intent.getParcelableExtra(EXTRA_PURCHASE);
        Settings settings = intent.getParcelableExtra(EXTRA_SETTINGS);
        if (settings == null) {
            settings = new Settings();
        }

        if (savedInstanceState == null) {
            // First-time init; create fragment to embed in activity.
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_header, HeaderFragment.newInstance(purchase, settings.getColor()))
                    .commit();
        }
    }
}
