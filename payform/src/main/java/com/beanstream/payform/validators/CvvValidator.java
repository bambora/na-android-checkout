/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.validators;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanstream.payform.Preferences;
import com.beanstream.payform.R;
import com.beanstream.payform.models.CardType;

import static com.beanstream.payform.models.CardType.getCvvLength;

/**
 * Created by dlight on 2016-08-17.
 */
public class CvvValidator extends TextValidator {

    private final EditText editText;
    private final ImageView imageView;

    public CvvValidator(TextView view, ImageView imageView) {
        super(view);

        this.editText = (EditText) view;
        this.imageView = imageView;

        updateCvvImage();
    }

    public static boolean isValidCvv(String cvv, String cardType) {
        return (cvv != null) && (cvv.length() == getCvvLength(cardType));
    }

    private void updateCvvImage() {
        if (TextUtils.isEmpty(editText.getError())) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        editText.setError(null);
        updateCvvImage();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        super.onFocusChange(view, hasFocus);

        if (hasFocus) {
            String cardType = Preferences.getInstance(view.getContext()).getData(Preferences.CARD_TYPE);
            setEditTextMaxLength(editText, CardType.getCvvLength(cardType));
        }
    }

    @Override
    public boolean validate(TextView view) {
        if (super.validate(view)) {
            String cvv = view.getText().toString();
            String cardType = Preferences.getInstance(view.getContext()).getData(Preferences.CARD_TYPE);

            if (isValidCvv(cvv, cardType)) {
                view.setError(null);
            } else {
                String error = view.getResources().getString(R.string.validator_prefix_invalid) + " " + view.getHint();
                view.setError(error);
            }
        }
        updateCvvImage();

        return TextUtils.isEmpty(view.getError());
    }
}
