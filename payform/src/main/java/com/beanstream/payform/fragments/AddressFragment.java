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
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.activities.BaseActivity;
import com.beanstream.payform.models.Address;
import com.beanstream.payform.validators.TextValidator;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment {

    final static String EXTRA_ADDRESS = "com.beanstream.payform.models.address";

    private Address address;

    public AddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            address = getArguments().getParcelable(EXTRA_ADDRESS);
        } else {
            address = new Address();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        configureBillingCheckBox(view);
        setValidators(view);
        updateAddress(view, address);
        updateTitle(view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showKeyboard();
    }

    private void setValidators(View view) {
        EditText textView;

        textView = (EditText) (view.findViewById(R.id.address_name));
        textView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.address_city));
        textView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.address_country));
        textView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.address_postal));
        textView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.address_province));
        textView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.address_street));
        textView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        textView.setOnFocusChangeListener(new TextValidator(textView));
    }

    public void updateTitle(View view) {
        ((TextView) view.findViewById(R.id.title_text)).setText(R.string.address_title_billing);
    }

    void configureBillingCheckBox(View view) {
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

    private void showKeyboard() {
        EditText textView = (EditText) (getActivity().findViewById(R.id.address_name));
        textView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        textView.requestFocus();
        BaseActivity.showKeyboard(getActivity());
    }

    private void updateAddress(View view, Address address) {
        ((TextView) view.findViewById(R.id.address_name)).setText(address.getName());
        ((TextView) view.findViewById(R.id.address_city)).setText(address.getCity());
        ((TextView) view.findViewById(R.id.address_country)).setText(address.getCountry());
        ((TextView) view.findViewById(R.id.address_postal)).setText(address.getPostal());
        ((TextView) view.findViewById(R.id.address_province)).setText(address.getProvince());
        ((TextView) view.findViewById(R.id.address_street)).setText(address.getStreet());
    }
}
