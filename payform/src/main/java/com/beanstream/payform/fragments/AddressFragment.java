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
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.models.Address;

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

    public Address getAddress() {

        Address address = new Address();

        address.setName(((TextView) getView().findViewById(R.id.address_name)).getText().toString());
        address.setCity(((TextView) getView().findViewById(R.id.address_city)).getText().toString());
        address.setCountry(((TextView) getView().findViewById(R.id.address_country)).getText().toString());
        address.setPostal(((TextView) getView().findViewById(R.id.address_postal)).getText().toString());
        address.setProvince(((TextView) getView().findViewById(R.id.address_province)).getText().toString());
        address.setStreet(((TextView) getView().findViewById(R.id.address_street)).getText().toString());

        return address;
    }
}
