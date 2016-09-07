/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.activities.PayFormActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingFragment extends AddressFragment {

    public final static String EXTRA_SETTINGS_BILLING_REQUIRED = "com.beanstream.payform.models.settings.color";
    OnBillingCheckBoxChangedListener mCallback;

    private boolean billingRequired;
    private int color;

    public ShippingFragment() {
        // Required empty public constructor
    }

    /**
     * @param billingRequired Billing address is required.
     * @param color           Primary color.
     * @return A new instance of fragment HeaderFragment.
     */
    public static ShippingFragment newInstance(boolean billingRequired, int color) {
        ShippingFragment fragment = new ShippingFragment();

        Bundle args = new Bundle();
        args.putBoolean(EXTRA_SETTINGS_BILLING_REQUIRED, billingRequired);
        args.putInt(PayFormActivity.EXTRA_SETTINGS_COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (OnBillingCheckBoxChangedListener) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (OnBillingCheckBoxChangedListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            billingRequired = getArguments().getBoolean(EXTRA_SETTINGS_BILLING_REQUIRED);
            color = getArguments().getInt(PayFormActivity.EXTRA_SETTINGS_COLOR);
        }
    }

    @Override
    public void updateTitle(View view) {
        ((TextView) view.findViewById(R.id.title_text)).setText(R.string.address_title_shipping);
    }

    @Override
    public void configureBillingCheckBox(View view) {
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.billing_switch);
        checkBox.setVisibility(View.GONE);

        if (billingRequired) {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mCallback.onBillingCheckBoxChanged(isChecked);
                }
            });
        }
    }

    public interface OnBillingCheckBoxChangedListener {
        public void onBillingCheckBoxChanged(boolean isChecked);
    }
}
