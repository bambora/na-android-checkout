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
import com.beanstream.payform.models.Address;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingFragment extends AddressFragment {

    public final static String EXTRA_BILLING_REQUIRED = "com.beanstream.payform.models.options.billingrequired";
    private static OnBillingCheckBoxChangedListener dummyBillingCallback = new OnBillingCheckBoxChangedListener() {
        @Override
        public void onBillingCheckBoxChanged(boolean isChecked) {
        }
    };
    private boolean billingRequired;
    private OnBillingCheckBoxChangedListener billingCallback = dummyBillingCallback;

    public ShippingFragment() {
        // Required empty public constructor
    }

    /**
     * @param address         Saved address.
     * @param billingRequired Whether or not to show billing checkbox.
     * @return A new instance of fragment ShippingFragment.
     */
    public static ShippingFragment newInstance(Address address, boolean billingRequired) {
        ShippingFragment fragment = new ShippingFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_ADDRESS, address);
        args.putBoolean(EXTRA_BILLING_REQUIRED, billingRequired);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            billingRequired = getArguments().getBoolean(EXTRA_BILLING_REQUIRED);
        } else {
            billingRequired = true;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        billingCallback = (OnBillingCheckBoxChangedListener) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        billingCallback = (OnBillingCheckBoxChangedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        billingCallback = dummyBillingCallback;
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
                    billingCallback.onBillingCheckBoxChanged(isChecked);
                }
            });
        }
    }

    public interface OnBillingCheckBoxChangedListener {
        void onBillingCheckBoxChanged(boolean isChecked);
    }
}
