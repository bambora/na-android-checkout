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
import android.widget.EditText;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.activities.PayFormActivity;
import com.beanstream.payform.models.CardType;
import com.beanstream.payform.models.CreditCard;
import com.beanstream.payform.models.Payment;
import com.beanstream.payform.validators.CreditCardValidator;
import com.beanstream.payform.validators.CvvValidator;
import com.beanstream.payform.validators.EmailValidator;
import com.beanstream.payform.validators.TextValidator;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {
    private int color;

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * @param color Primary color.
     * @return A new instance of fragment PaymentFragment.
     */
    public static PaymentFragment newInstance(int color) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putInt(PayFormActivity.EXTRA_SETTINGS_COLOR, color);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        setValidators(view);
        updatePrimaryColor(view);

        return view;
    }

    private void updatePrimaryColor(View view) {
        ((TextView) view.findViewById(R.id.back_link)).setTextColor(color);
        ((TextView) view.findViewById(R.id.title_text)).setTextColor(color);
    }

    public Payment getPayment() {
        Payment payment = new Payment();

        payment.setName(((TextView) getView().findViewById(R.id.pay_name)).getText().toString());
        payment.setEmail(((TextView) getView().findViewById(R.id.pay_email)).getText().toString());

        return payment;
    }

    public void setValidators(View view) {
        EditText textView;

        textView = (EditText) (view.findViewById(R.id.pay_email));
        textView.setOnFocusChangeListener(new EmailValidator(textView));

        textView = (EditText) (view.findViewById(R.id.pay_name));
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.pay_card_number));
        textView.addTextChangedListener(new CreditCardValidator(textView));
        textView.setOnFocusChangeListener(new CreditCardValidator(textView));

        textView = (EditText) (view.findViewById(R.id.pay_expiry));
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.pay_cvv));
        textView.setOnFocusChangeListener(new CvvValidator(textView));
    }

    public CreditCard getCreditCard() {

        String cardNumber = ((TextView) getView().findViewById(R.id.pay_card_number)).getText().toString();
        cardNumber = cardNumber.replace(" ", "");
        String cvv = ((TextView) getView().findViewById(R.id.pay_cvv)).getText().toString();

        String expiry = ((TextView) getView().findViewById(R.id.pay_expiry)).getText().toString();
        String month = "";
        String year = "";
        if (expiry.length() > 0) {
            month = expiry.substring(0, 2);
            year = expiry.substring(2);
        }

        CreditCard card = new CreditCard();

        card.setCardNumber(cardNumber);
        card.setCvv(cvv);
        card.setExpiryMonth(month);
        card.setExpiryYear(year);
        card.setCardType(CardType.getCardTypeFromCardNumber(cardNumber));

        return card;
    }
}
