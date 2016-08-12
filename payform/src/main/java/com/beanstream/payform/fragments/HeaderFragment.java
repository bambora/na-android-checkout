/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.activities.PayFormActivity;
import com.beanstream.payform.models.Purchase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeaderFragment extends Fragment {

    public final static String EXTRA_SETTINGS_COLOR = "com.beanstream.payform.models.settings.color";

    private Purchase purchase;
    private int color;

    public HeaderFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param purchase Purchase info.
     * @param color    Primary color.
     * @return A new instance of fragment HeaderFragment.
     */
    public static HeaderFragment newInstance(Purchase purchase, int color) {
        HeaderFragment fragment = new HeaderFragment();
        Bundle args = new Bundle();
        args.putParcelable(PayFormActivity.EXTRA_PURCHASE, purchase);
        args.putInt(EXTRA_SETTINGS_COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            purchase = getArguments().getParcelable(PayFormActivity.EXTRA_PURCHASE);
            color = getArguments().getInt(EXTRA_SETTINGS_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_header, container, false);

        ((RelativeLayout) inflatedView.findViewById(R.id.fragment_header)).setBackgroundColor(color);

        ((TextView) inflatedView.findViewById(R.id.header_company_name)).setText(purchase.getCompanyName());
        ((TextView) inflatedView.findViewById(R.id.purchase_amount)).setText(purchase.getFormattedAmount());
        ((TextView) inflatedView.findViewById(R.id.purchase_description)).setText(purchase.getDescription());

        return inflatedView;
    }
}
