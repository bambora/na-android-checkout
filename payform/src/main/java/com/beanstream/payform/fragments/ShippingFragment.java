/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.beanstream.payform.R;
import com.beanstream.payform.models.Address;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingFragment extends Fragment {

    public final static String EXTRA_SETTINGS_BILLING_REQUIRED = "com.beanstream.payform.models.settings.color";
    OnBillingCheckBoxChangedListener mCallback;

    private boolean billingRequired;

    public ShippingFragment() {
        // Required empty public constructor
    }

    /**
     * @param billingRequired Billing address is required.
     * @return A new instance of fragment HeaderFragment.
     */
    public static ShippingFragment newInstance(boolean billingRequired) {
        ShippingFragment fragment = new ShippingFragment();
        Bundle args = new Bundle();
        args.putBoolean(EXTRA_SETTINGS_BILLING_REQUIRED, billingRequired);
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
        }

        getChildFragmentManager().beginTransaction().replace(R.id.fragment_address, new AddressFragment()).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipping, container, false);

        configureBillingCheckBox(view);

        return view;
    }

    private void configureBillingCheckBox(View view) {
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.billing_switch);
        checkBox.setVisibility(View.INVISIBLE);

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

    public Address getAddress() {
        return ((AddressFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_address))
                .getAddress();
    }

    public interface OnBillingCheckBoxChangedListener {
        public void onBillingCheckBoxChanged(boolean isChecked);
    }
}
