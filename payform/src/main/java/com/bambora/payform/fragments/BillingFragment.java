/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.payform.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bambora.payform.R;
import com.bambora.payform.models.Address;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillingFragment extends AddressFragment {

    public BillingFragment() {
        // Required empty public constructor
    }

    /**
     * @param address Saved address.
     * @return A new instance of fragment PaymentFragment.
     */
    public static BillingFragment newInstance(Address address) {
        BillingFragment fragment = new BillingFragment();

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_ADDRESS, address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void updateTitle(View view) {
        ((TextView) view.findViewById(R.id.title_text)).setText(R.string.address_title_billing);
    }
}
