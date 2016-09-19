/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.models.Address;

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
