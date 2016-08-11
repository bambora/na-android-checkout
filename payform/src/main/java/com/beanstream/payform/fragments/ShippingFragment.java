/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beanstream.payform.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingFragment extends Fragment {

    public final static String EXTRA_SETTINGS_BILLING_REQUIRED = "com.beanstream.payform.models.settings.color";

    private boolean billingRequired;

    public ShippingFragment() {
        // Required empty public constructor
    }

    /**
     *
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            billingRequired = getArguments().getParcelable(EXTRA_SETTINGS_BILLING_REQUIRED);
        }

        getChildFragmentManager().beginTransaction().replace(R.id.fragment_address, new AddressFragment()).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_shipping, container, false);

        if (billingRequired) {
            inflatedView.findViewById(R.id.billing_switch).setVisibility(View.VISIBLE);
        } else {
            inflatedView.findViewById(R.id.billing_switch).setVisibility(View.INVISIBLE);
        }

        return inflatedView;
    }

}
