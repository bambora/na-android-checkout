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
import com.beanstream.payform.models.Payment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {
    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_payment, container, false);

        return inflatedView;
    }

    public Payment getPayment() {
        Payment payment = new Payment();

        payment.setName(((TextView) getView().findViewById(R.id.pay_name)).getText().toString());
        payment.setCardNumber(((TextView) getView().findViewById(R.id.pay_card_number)).getText().toString());
        payment.setCvv(((TextView) getView().findViewById(R.id.pay_cvv)).getText().toString());
        payment.setEmail(((TextView) getView().findViewById(R.id.pay_email)).getText().toString());
        payment.setExpiry(((TextView) getView().findViewById(R.id.pay_expiry)).getText().toString());

        return payment;
    }
}
