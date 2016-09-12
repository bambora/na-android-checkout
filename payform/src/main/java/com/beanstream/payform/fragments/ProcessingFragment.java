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
import com.beanstream.payform.models.Options;
import com.beanstream.payform.models.Purchase;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProcessingFragment extends Fragment {

    private Options options;
    private Purchase purchase;

    public ProcessingFragment() {
        // Required empty public constructor
    }

    /**
     * @param options PayForm options.
     * @param purchase Purchase info.
     * @return A new instance of fragment ProcessingFragment.
     */
    public static ProcessingFragment newInstance(Options options, Purchase purchase) {
        ProcessingFragment fragment = new ProcessingFragment();
        Bundle args = new Bundle();
        args.putParcelable(PayFormActivity.EXTRA_OPTIONS, options);
        args.putParcelable(PayFormActivity.EXTRA_PURCHASE, purchase);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            options = getArguments().getParcelable(PayFormActivity.EXTRA_OPTIONS);
            purchase = getArguments().getParcelable(PayFormActivity.EXTRA_PURCHASE);
        } else {
            options = new Options();
            purchase = new Purchase(0.0, "");
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

        ((TextView) view.findViewById(R.id.processing_text)).setTextColor(options.getColor());
        ((TextView) view.findViewById(R.id.processing_amount)).setTextColor(options.getColor());
        ((TextView) view.findViewById(R.id.processing_target)).setTextColor(options.getColor());
    }
}
