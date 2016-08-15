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

    public ProcessingFragment() {
        // Required empty public constructor
    }

    /**
     * @param purchase Purchase info.
     * @return A new instance of fragment HeaderFragment.
     */
    public static ProcessingFragment newInstance(Purchase purchase) {
        ProcessingFragment fragment = new ProcessingFragment();
        Bundle args = new Bundle();
        args.putParcelable(PayFormActivity.EXTRA_PURCHASE, purchase);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            purchase = getArguments().getParcelable(PayFormActivity.EXTRA_PURCHASE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_processing, container, false);
        TextView view = ((TextView) inflatedView.findViewById(R.id.processing_amount));
        if (view != null) {
            view.setText(purchase.getFormattedAmount());
        }

        return inflatedView;
    }

}
