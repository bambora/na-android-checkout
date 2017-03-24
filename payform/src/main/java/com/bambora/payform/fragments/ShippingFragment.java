/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.payform.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bambora.payform.R;
import com.bambora.payform.models.Address;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingFragment extends AddressFragment {

    private final static String EXTRA_BILLING_REQUIRED = "com.beanstream.payform.models.options.billingrequired";
    private final static String EXTRA_BILLING_SAME_AS_SHIPPING = "com.beanstream.payform.billing.sameas.shipping";

    private static final OnBillingCheckBoxChangedListener dummyBillingCallback = new OnBillingCheckBoxChangedListener() {
        @Override
        public void onBillingCheckBoxChanged(boolean isChecked) {
        }
    };
    private boolean isBillingRequired;
    private boolean isBillingSameAsShipping;
    private OnBillingCheckBoxChangedListener billingCallback = dummyBillingCallback;

    public ShippingFragment() {
        // Required empty public constructor
    }

    /**
     * @param address         Saved address.
     * @param billingRequired Whether or not to show billing checkbox.
     * @return A new instance of fragment ShippingFragment.
     */
    public static ShippingFragment newInstance(Address address, boolean billingRequired, boolean billingSameAsShipping) {
        ShippingFragment fragment = new ShippingFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_ADDRESS, address);
        args.putBoolean(EXTRA_BILLING_REQUIRED, billingRequired);
        args.putBoolean(EXTRA_BILLING_SAME_AS_SHIPPING, billingSameAsShipping);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            isBillingRequired = getArguments().getBoolean(EXTRA_BILLING_REQUIRED);
            isBillingSameAsShipping = getArguments().getBoolean(EXTRA_BILLING_SAME_AS_SHIPPING);
        } else {
            isBillingRequired = true;
            isBillingSameAsShipping = false;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        checkBox.setOnCheckedChangeListener(null);
        if (isBillingRequired) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isBillingSameAsShipping = isChecked;
                    billingCallback.onBillingCheckBoxChanged(isChecked);
                }
            });
            checkBox.setChecked(isBillingSameAsShipping);
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }
    }

    public boolean getIsBillingSameAsShipping() {
        return ((CheckBox) getView().findViewById(R.id.billing_switch)).isChecked();
    }

    public interface OnBillingCheckBoxChangedListener {
        void onBillingCheckBoxChanged(boolean isChecked);
    }
}
