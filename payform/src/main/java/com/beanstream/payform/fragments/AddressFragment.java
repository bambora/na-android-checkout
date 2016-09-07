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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.activities.PayFormActivity;
import com.beanstream.payform.models.Address;
import com.beanstream.payform.validators.TextValidator;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment {

    private int color;

    public AddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            color = getArguments().getInt(PayFormActivity.EXTRA_SETTINGS_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        configureBillingCheckBox(view);
        setValidators(view);
        updatePrimaryColor(view);
        updateTitle(view);

        return view;
    }

    public void setValidators(View view) {
        EditText textView;

        textView = (EditText) (view.findViewById(R.id.address_name));
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.address_city));
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.address_country));
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.address_postal));
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.address_province));
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.address_street));
        textView.setOnFocusChangeListener(new TextValidator(textView));
    }

    public void updatePrimaryColor(View view) {
        ((TextView) view.findViewById(R.id.title_text)).setTextColor(color);
    }

    public void updateTitle(View view) {
        ((TextView) view.findViewById(R.id.title_text)).setText(R.string.address_title_billing);
    }

    public void configureBillingCheckBox(View view) {
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.billing_switch);
        checkBox.setVisibility(View.GONE);
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
