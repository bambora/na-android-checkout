/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.payform;

import android.os.Build;

import com.bambora.payform.models.CardType;
import com.bambora.payform.validators.CvvValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by dlight on 2016-08-18.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class CvvValidatorTest {
    private final static String cvv_valid = "123";
    private final static String cvv_valid_amex = "1234";
    private final static String cvv_invalid_long = "12345";
    private final static String cvv_invalid_short = "12";

    //region isValidCvv
    @Test
    public void cvvValidator_Cvv_Correct_ReturnsTrue() {
        assertTrue(CvvValidator.isValidCvv(cvv_valid_amex, CardType.AMEX));
        assertTrue(CvvValidator.isValidCvv(cvv_valid, CardType.DINERS));
        assertTrue(CvvValidator.isValidCvv(cvv_valid, CardType.DISCOVER));
        assertTrue(CvvValidator.isValidCvv(cvv_valid, CardType.MASTERCARD));
        assertTrue(CvvValidator.isValidCvv(cvv_valid, CardType.VISA));
    }

    @Test
    public void cvvValidator_Cvv_Empty_ReturnsFalse() {
        assertFalse("empty", CvvValidator.isValidCvv("", CardType.AMEX));
        assertFalse("whitespace", CvvValidator.isValidCvv(" ", CardType.AMEX));
        assertFalse("null", CvvValidator.isValidCvv(null, CardType.AMEX));
    }

    @Test
    public void cvvValidator_Cvv_Invalid_ReturnsFalse() {
        assertFalse(CvvValidator.isValidCvv(cvv_valid, CardType.AMEX));
        assertFalse(CvvValidator.isValidCvv(cvv_valid_amex, CardType.DINERS));
        assertFalse(CvvValidator.isValidCvv(cvv_invalid_long, CardType.DINERS));
        assertFalse(CvvValidator.isValidCvv(cvv_invalid_short, CardType.DISCOVER));
        assertFalse(CvvValidator.isValidCvv(cvv_invalid_long, CardType.MASTERCARD));
        assertFalse(CvvValidator.isValidCvv(cvv_invalid_short, CardType.VISA));
    }
    //endregion
}

