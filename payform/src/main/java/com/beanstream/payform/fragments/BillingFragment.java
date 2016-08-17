/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.activities.PayFormActivity;
import com.beanstream.payform.models.Address;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillingFragment extends Fragment {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            color = getArguments().getInt(PayFormActivity.EXTRA_SETTINGS_COLOR);
        }

        getChildFragmentManager().beginTransaction().replace(R.id.fragment_address, new AddressFragment()).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billing, container, false);

        updatePrimaryColor(view);

        return view;
    }

    private void updatePrimaryColor(View view) {
        ((TextView) view.findViewById(R.id.title_text)).setTextColor(color);
    }

    public Address getAddress() {
        return ((AddressFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_address))
                .getAddress();
    }
}
