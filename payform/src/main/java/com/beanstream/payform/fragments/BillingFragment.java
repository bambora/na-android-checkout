/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.activities.PayFormActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillingFragment extends AddressFragment {

    private int color;

    public BillingFragment() {
        // Required empty public constructor
    }

    /**
     * @param color Primary color.
     * @return A new instance of fragment PaymentFragment.
     */
    public static BillingFragment newInstance(int color) {
        BillingFragment fragment = new BillingFragment();

        Bundle args = new Bundle();
        args.putInt(PayFormActivity.EXTRA_SETTINGS_COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void updateTitle(View view) {
        ((TextView) view.findViewById(R.id.title_text)).setText(R.string.address_title_billing);
    }
}
