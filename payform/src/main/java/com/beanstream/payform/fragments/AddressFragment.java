/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 *
 * Created by dlight on 2016-08-08.
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
public class AddressFragment extends Fragment {
    public AddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_address, container, false);

        return inflatedView;
    }
}
