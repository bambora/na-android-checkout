/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 *
 * Created by dlight on 2016-08-12.
 *
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
import com.beanstream.payform.models.Purchase;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProcessingFragment extends Fragment {

    private Purchase purchase;
    private int color;

    public ProcessingFragment() {
        // Required empty public constructor
    }

    /**
     * @param purchase Purchase info.
     * @param color    Primary color.
     * @return A new instance of fragment ProcessingFragment.
     */
    public static ProcessingFragment newInstance(Purchase purchase, int color) {
        ProcessingFragment fragment = new ProcessingFragment();
        Bundle args = new Bundle();
        args.putParcelable(PayFormActivity.EXTRA_PURCHASE, purchase);
        args.putInt(PayFormActivity.EXTRA_SETTINGS_COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            purchase = getArguments().getParcelable(PayFormActivity.EXTRA_PURCHASE);
            color = getArguments().getInt(PayFormActivity.EXTRA_SETTINGS_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_processing, container, false);

        updateAmount(view);
        updatePrimaryColor(view);

        return view;
    }

    private void updateAmount(View view) {

        TextView textView = ((TextView) view.findViewById(R.id.processing_amount));
        if (textView != null) {
            textView.setText(purchase.getFormattedAmount());
        }
    }

    private void updatePrimaryColor(View view) {

        ((TextView) view.findViewById(R.id.processing_text)).setTextColor(color);
        ((TextView) view.findViewById(R.id.processing_amount)).setTextColor(color);
        ((TextView) view.findViewById(R.id.processing_target)).setTextColor(color);
    }
}
