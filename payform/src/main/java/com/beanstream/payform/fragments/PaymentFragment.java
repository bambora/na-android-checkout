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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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
    private final static String EXTRA_CARD_NUMBER_ERROR = "com.beanstream.payform.payfragment.cardnumber.error";
    private final static String EXTRA_CVV_ERROR = "com.beanstream.payform.payfragment.cvv.error";

    private final static String EXTRA_EXPIRY_MONTH = "com.beanstream.payform.payfragment.expiry.month";
    private final static String EXTRA_EXPIRY_YEAR = "com.beanstream.payform.payfragment.expiry.year";

    private CardInfo cardInfo;

    private Spinner monthSpinner;
    private Spinner yearSpinner;

    private String expiryMonth;
    private String expiryYear;

    private EditText cardNumberEditText;
    private EditText cvvEditText;

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

        monthSpinner = (Spinner) view.findViewById(R.id.pay_expiry_month);
        yearSpinner = (Spinner) view.findViewById(R.id.pay_expiry_year);

        cardNumberEditText = (EditText) view.findViewById(R.id.pay_card_number);
        cvvEditText = (EditText) view.findViewById(R.id.pay_cvv);

        // Restore errors where fields have images
        String cardNumberError = null;
        String cvvError = null;

        // Restore expiry spinners
        expiryMonth = null;
        expiryYear = null;

        if (savedInstanceState != null) {
            cardNumberError = savedInstanceState.getString(EXTRA_CARD_NUMBER_ERROR);
            cvvError = savedInstanceState.getString(EXTRA_CVV_ERROR);

            expiryMonth = savedInstanceState.getString(EXTRA_EXPIRY_MONTH);
            expiryYear = savedInstanceState.getString(EXTRA_EXPIRY_YEAR);
        }

        cardNumberEditText.setError(cardNumberError);
        cvvEditText.setError(cvvError);

        updateCardInfo(view, cardInfo);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        View view = getView();
        setValidators(view);

        EditText editText = (EditText) view.findViewById(R.id.pay_email);
        BaseActivity.showKeyboardWhenEmpty(getActivity(), editText);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        CharSequence error = cardNumberEditText.getError();
        cardNumberEditText.setError(null);
        String cardErrorText = (error == null ? null : error.toString());
        outState.putString(EXTRA_CARD_NUMBER_ERROR, cardErrorText);

        error = cvvEditText.getError();
        cvvEditText.setError(null);
        String cvvErrorText = (error == null ? null : error.toString());
        outState.putString(EXTRA_CVV_ERROR, cvvErrorText);

        outState.putString(EXTRA_EXPIRY_MONTH, monthSpinner.getSelectedItem().toString());
        outState.putString(EXTRA_EXPIRY_YEAR, yearSpinner.getSelectedItem().toString());
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

        String month = monthSpinner.getSelectedItem().toString();
        String year = yearSpinner.getSelectedItem().toString();

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

        cardNumberEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        ImageView cardImage = (ImageView) (view.findViewById(R.id.pay_card_image));

        CardNumberValidator validator = new CardNumberValidator(cardNumberEditText, cardImage);
        cardNumberEditText.addTextChangedListener(validator);
        cardNumberEditText.setOnFocusChangeListener(validator);

        ExpiryValidator monthValidator = new ExpiryValidator(monthSpinner);
        monthSpinner.setAdapter(adapterWithList(ExpiryValidator.expiryMonths(), getResources().getString(R.string.pay_hint_expiry_month)));
        monthSpinner.setOnItemSelectedListener(monthValidator);
        monthSpinner.setOnTouchListener(monthValidator);
        SpinnerAdapter.selectItem(monthSpinner, expiryMonth);

        ExpiryValidator yearValidator = new ExpiryValidator(yearSpinner);
        yearSpinner.setAdapter(adapterWithList(ExpiryValidator.expiryYears(), getResources().getString(R.string.pay_hint_expiry_year)));
        yearSpinner.setOnItemSelectedListener(yearValidator);
        yearSpinner.setOnTouchListener(yearValidator);
        SpinnerAdapter.selectItem(yearSpinner, expiryYear);

        cvvEditText = (EditText) (view.findViewById(R.id.pay_cvv));
        cvvEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        ImageView cvvImage = (ImageView) (view.findViewById(R.id.pay_cvv_image));

        CvvValidator cvvValidator = new CvvValidator(cvvEditText, cvvImage);
        cvvEditText.addTextChangedListener(cvvValidator);
        cvvEditText.setOnFocusChangeListener(cvvValidator);
    }

    private ArrayAdapter<String> adapterWithList(ArrayList<String> list, String hint) {
        return new SpinnerAdapter(this.getActivity(), R.layout.expiry_spinner_item, list, hint);
    }

    private void updateCardInfo(View view, CardInfo cardInfo) {
        ((TextView) view.findViewById(R.id.pay_name)).setText(cardInfo.getName());
        ((TextView) view.findViewById(R.id.pay_email)).setText(cardInfo.getEmail());
    }
}
