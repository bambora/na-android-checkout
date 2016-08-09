/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.activities.PayFormActivity;

import java.text.NumberFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeaderFragment extends Fragment {
    private Double mAmount;
    private String mCurrency;
    private String mDescription;

    private String mName;
    private String mColor;

    public HeaderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param purchaseAmount      Purchase amount.
     * @param purchaseCurrency    Currency code.
     * @param purchaseDescription Purchase description.
     * @param companyName         Company name.
     * @param primaryColor        Parameter 2.
     * @return A new instance of fragment HeaderFragment.
     */
    public static HeaderFragment newInstance(Double purchaseAmount, String purchaseCurrency, String purchaseDescription, String companyName, String primaryColor) {
        HeaderFragment fragment = new HeaderFragment();
        Bundle args = new Bundle();
        args.putDouble(PayFormActivity.EXTRA_PURCHASE_AMOUNT, purchaseAmount);
        args.putString(PayFormActivity.EXTRA_PURCHASE_CURRENCY_CODE, purchaseCurrency);
        args.putString(PayFormActivity.EXTRA_PURCHASE_DESCRIPTION, purchaseDescription);
        args.putString(PayFormActivity.EXTRA_COMPANY_NAME, companyName);
        args.putString(PayFormActivity.EXTRA_PRIMARY_COLOR, primaryColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAmount = getArguments().getDouble(PayFormActivity.EXTRA_PURCHASE_AMOUNT);
            mCurrency = getArguments().getString(PayFormActivity.EXTRA_PURCHASE_CURRENCY_CODE);
            mDescription = getArguments().getString(PayFormActivity.EXTRA_PURCHASE_DESCRIPTION);
            mName = getArguments().getString(PayFormActivity.EXTRA_COMPANY_NAME);
            mColor = getArguments().getString(PayFormActivity.EXTRA_PRIMARY_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_header, container, false);

        if (mColor != null) {
            ((RelativeLayout) inflatedView.findViewById(R.id.fragment_header)).setBackgroundColor(Color.parseColor(mColor));
        }
        ((TextView) inflatedView.findViewById(R.id.pay_merchant_name)).setText(mName);

        if (mAmount != null) {
            ((TextView) inflatedView.findViewById(R.id.pay_amount)).setText(NumberFormat.getCurrencyInstance().format(mAmount));
        }

        ((TextView) inflatedView.findViewById(R.id.pay_description)).setText(mCurrency);
        ((TextView) inflatedView.findViewById(R.id.pay_description)).setText(mDescription);

        return inflatedView;
    }
}
