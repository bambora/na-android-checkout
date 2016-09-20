/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 *
 * Created by dlight on 2016-08-08.
 */

package com.beanstream.payform.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.beanstream.payform.R;
import com.beanstream.payform.activities.BaseActivity;
import com.beanstream.payform.adapters.SpinnerAdapter;
import com.beanstream.payform.models.CardInfo;
import com.beanstream.payform.models.CardType;
import com.beanstream.payform.models.CreditCard;
import com.beanstream.payform.validators.CardNumberValidator;
import com.beanstream.payform.validators.CvvValidator;
import com.beanstream.payform.validators.EmailValidator;
import com.beanstream.payform.validators.ExpiryValidator;
import com.beanstream.payform.validators.TextValidator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {
    private final static String EXTRA_CARDINFO = "com.beanstream.payform.models.cardinfo";

    private CardInfo cardInfo;

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * @param cardInfo Non-sensitive card information.
     * @return A new instance of fragment PaymentFragment.
     */
    public static PaymentFragment newInstance(CardInfo cardInfo) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_CARDINFO, cardInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardInfo = getArguments().getParcelable(EXTRA_CARDINFO);
        } else {
            cardInfo = new CardInfo();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        setValidators(view);
        updateCardInfo(view, cardInfo);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showKeyboard();
    }

    public CardInfo getCardInfo() {
        CardInfo cardInfo = new CardInfo();

        cardInfo.setName(((TextView) getView().findViewById(R.id.pay_name)).getText().toString());
        cardInfo.setEmail(((TextView) getView().findViewById(R.id.pay_email)).getText().toString());

        return cardInfo;
    }

    public CreditCard getCreditCard() {

        String cardNumber = ((TextView) getView().findViewById(R.id.pay_card_number)).getText().toString();
        cardNumber = cardNumber.replace(" ", "");
        String cvv = ((TextView) getView().findViewById(R.id.pay_cvv)).getText().toString();

        String month = ((Spinner) getView().findViewById(R.id.pay_expiry_month)).getSelectedItem().toString();
        String year = ((Spinner) getView().findViewById(R.id.pay_expiry_year)).getSelectedItem().toString();

        CreditCard card = new CreditCard();

        card.setCardNumber(cardNumber);
        card.setCvv(cvv);
        card.setExpiryMonth(month);
        card.setExpiryYear(year);
        card.setCardType(CardType.getCardTypeFromCardNumber(cardNumber));

        return card;
    }

    private void setValidators(View view) {
        EditText textView;

        textView = (EditText) (view.findViewById(R.id.pay_email));
        textView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        textView.setOnFocusChangeListener(new EmailValidator(textView));

        textView = (EditText) (view.findViewById(R.id.pay_name));
        textView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        textView.setOnFocusChangeListener(new TextValidator(textView));

        textView = (EditText) (view.findViewById(R.id.pay_card_number));
        textView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        textView.addTextChangedListener(new CardNumberValidator(textView));
        textView.setOnFocusChangeListener(new CardNumberValidator(textView));

        Spinner spinner = (Spinner) view.findViewById(R.id.pay_expiry_month);
        configureSpinnerForExpiry(spinner, R.string.pay_hint_expiry_month, ExpiryValidator.expiryMonths());

        spinner = (Spinner) view.findViewById(R.id.pay_expiry_year);
        configureSpinnerForExpiry(spinner, R.string.pay_hint_expiry_year, ExpiryValidator.expiryYears());

        textView = (EditText) (view.findViewById(R.id.pay_cvv));
        textView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        textView.setOnFocusChangeListener(new CvvValidator(textView));
    }

    private void configureSpinnerForExpiry(Spinner spinner, int hintResourceId, ArrayList<String> list) {
        spinner.setAdapter(adapterWithList(list, getResources().getString(hintResourceId)));
        spinner.setOnItemSelectedListener(new ExpiryValidator(spinner));
    }

    private ArrayAdapter<String> adapterWithList(ArrayList<String> list, String hint) {
        return new SpinnerAdapter(this.getActivity(), R.layout.expiry_spinner_item, list, hint);
    }

    private void showKeyboard() {
        EditText textView = (EditText) (getActivity().findViewById(R.id.pay_email));
        textView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        textView.requestFocus();
        BaseActivity.showKeyboard(getActivity());
    }

    private void updateCardInfo(View view, CardInfo cardInfo) {
        ((TextView) view.findViewById(R.id.pay_name)).setText(cardInfo.getName());
        ((TextView) view.findViewById(R.id.pay_email)).setText(cardInfo.getEmail());
    }
}
