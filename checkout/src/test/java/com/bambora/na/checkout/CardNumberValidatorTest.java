/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.na.checkout;

import android.os.Build;

import com.bambora.na.checkout.models.CardType;
import com.bambora.na.checkout.validators.CardNumberValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by dlight on 2016-08-17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class CardNumberValidatorTest {
    private final static String cardnumber_luhn_valid = "1234 5678 9012 3452";

    public final static String amex_valid = "3782 8224 6310 005";
    public final static String amex_invalid_cardtype = "328282246310005";
    private final static String amex_invalid_number = "37828224631000555";
    private final static String amex_invalid_luhn = "378282246310015";

    public final static String discover_valid = "6011 0009 9013 9424";
    public final static String discover_invalid_cardtype = "5011000990139424";
    private final static String discover_invalid_number = "60110009901394244";
    private final static String discover_invalid_luhn = "6011000990139434";

    public final static String diners_valid = "3852 0000 0232 37";
    public final static String diners_invalid_cardtype = "30620000023237";
    private final static String diners_invalid_number = "385200000232377";
    private final static String diners_invalid_luhn = "38520000023247";

    public final static String mastercard_valid = "5555 5555 5555 4444";
    public final static String mastercard_invalid_cardtype = "5655 5555 5555 4444";
    private final static String mastercard_invalid_number = "5555 5555 5555 44444";
    private final static String mastercard_invalid_luhn = "5555 5555 5555 4443";

    public final static String mastercard_bintwo_valid = "2221 0000 0000 0009";
    private final static String mastercard_bintwo_invalid_number = "2221 0000 0000 00090";
    private final static String mastercard_bintwo_invalid_luhn = "2720 9999 9999 9999";

    public final static String visa_valid = "4012 8888 8888 1881";
    public final static String visa_invalid_cardtype = "1012888888881881";
    private final static String visa_invalid_number = "3012888888881881";
    private final static String visa_invalid_luhn = "4012888888882881";

    //region isValidCardNumber
    @Test
    public void cardValidator_CardNumber_Correct_ReturnsTrue() {
        assertTrue(CardNumberValidator.isValidCardNumber(amex_valid, CardType.AMEX));
        assertTrue(CardNumberValidator.isValidCardNumber(diners_valid, CardType.DINERS));
        assertTrue(CardNumberValidator.isValidCardNumber(discover_valid, CardType.DISCOVER));
        assertTrue(CardNumberValidator.isValidCardNumber(mastercard_valid, CardType.MASTERCARD));
        assertTrue(CardNumberValidator.isValidCardNumber(mastercard_bintwo_valid, CardType.MASTERCARD));
        assertTrue(CardNumberValidator.isValidCardNumber(visa_valid, CardType.VISA));
    }

    @Test
    public void cardValidator_CardNumber_Empty_ReturnsFalse() {
        assertFalse("empty", CardNumberValidator.isValidCardNumber("", CardType.VISA));
        assertFalse("whitespace", CardNumberValidator.isValidCardNumber(" ", CardType.VISA));
        assertFalse("null", CardNumberValidator.isValidCardNumber(null, CardType.VISA));

        assertFalse("empty", CardNumberValidator.isValidCardNumber(amex_valid, ""));
        assertFalse("whitespace", CardNumberValidator.isValidCardNumber(amex_valid, " "));
        assertFalse("null", CardNumberValidator.isValidCardNumber(amex_valid, null));
    }

    @Test
    public void cardValidator_CardNumber_Invalid_ReturnsFalse() {
        assertFalse(CardNumberValidator.isValidCardNumber(amex_invalid_number, CardType.AMEX));
        assertFalse(CardNumberValidator.isValidCardNumber(diners_invalid_number, CardType.DINERS));
        assertFalse(CardNumberValidator.isValidCardNumber(discover_invalid_number, CardType.DISCOVER));
        assertFalse(CardNumberValidator.isValidCardNumber(mastercard_invalid_number, CardType.MASTERCARD));
        assertFalse(CardNumberValidator.isValidCardNumber(mastercard_bintwo_invalid_number, CardType.MASTERCARD));
        assertFalse(CardNumberValidator.isValidCardNumber(visa_invalid_number, CardType.VISA));

        assertFalse(CardNumberValidator.isValidCardNumber(amex_valid + "1", CardType.AMEX));
        assertFalse(CardNumberValidator.isValidCardNumber(diners_valid + "1", CardType.DINERS));
        assertFalse(CardNumberValidator.isValidCardNumber(discover_valid + "1", CardType.DISCOVER));
        assertFalse(CardNumberValidator.isValidCardNumber(mastercard_valid + "1", CardType.MASTERCARD));
        assertFalse(CardNumberValidator.isValidCardNumber(visa_valid + "1", CardType.VISA));
    }
    //endregion

    //region isValidCardType

    @Test
    public void cardValidator_CardType_Correct_ReturnsTrue() {
        assertTrue(CardNumberValidator.isValidCardType(amex_valid));
        assertTrue(CardNumberValidator.isValidCardType(diners_valid));
        assertTrue(CardNumberValidator.isValidCardType(discover_valid));
        assertTrue(CardNumberValidator.isValidCardType(mastercard_valid));
        assertTrue(CardNumberValidator.isValidCardType(mastercard_bintwo_valid));
        assertTrue(CardNumberValidator.isValidCardType(visa_valid));
    }

    @Test
    public void cardValidator_CardType_Empty_ReturnsFalse() {
        assertFalse("empty", CardNumberValidator.isValidCardType(""));
        assertFalse("whitespace", CardNumberValidator.isValidCardType(" "));
        assertFalse("null", CardNumberValidator.isValidCardType(null));
    }

    @Test
    public void cardValidator_CardType_Invalid_ReturnsFalse() {
        assertFalse("amex", CardNumberValidator.isValidCardType(amex_invalid_cardtype));
        assertFalse("diners", CardNumberValidator.isValidCardType(diners_invalid_cardtype));
        assertFalse("discover", CardNumberValidator.isValidCardType(discover_invalid_cardtype));
        assertFalse("mastercard", CardNumberValidator.isValidCardType(mastercard_invalid_cardtype));
        assertFalse("visa", CardNumberValidator.isValidCardType(visa_invalid_cardtype));
    }
    //endregion

    //region isValidLuhn
    @Test
    public void cardValidator_Luhn_Correct_ReturnsTrue() {
        assertTrue(CardNumberValidator.isValidLuhn(cardnumber_luhn_valid));

        assertTrue(CardNumberValidator.isValidLuhn(amex_valid));
        assertTrue(CardNumberValidator.isValidLuhn(diners_valid));
        assertTrue(CardNumberValidator.isValidLuhn(discover_valid));
        assertTrue(CardNumberValidator.isValidLuhn(mastercard_valid));
        assertTrue(CardNumberValidator.isValidLuhn(mastercard_bintwo_valid));
        assertTrue(CardNumberValidator.isValidLuhn(visa_valid));
    }

    @Test
    public void cardValidator_Luhn_Empty_ReturnsFalse() {
        assertFalse("empty", CardNumberValidator.isValidLuhn(""));
        assertFalse("whitespace", CardNumberValidator.isValidLuhn(" "));
        assertFalse("null", CardNumberValidator.isValidLuhn(null));
    }

    @Test
    public void cardValidator_Luhn_Invalid_ReturnsFalse() {
        assertFalse("amex", CardNumberValidator.isValidLuhn(amex_invalid_luhn));
        assertFalse("discover", CardNumberValidator.isValidLuhn(discover_invalid_luhn));
        assertFalse("diners", CardNumberValidator.isValidLuhn(diners_invalid_luhn));
        assertFalse("mastercard", CardNumberValidator.isValidLuhn(mastercard_invalid_luhn));
        assertFalse("mastercard bin two", CardNumberValidator.isValidLuhn(mastercard_bintwo_invalid_luhn));
        assertFalse("visa", CardNumberValidator.isValidLuhn(visa_invalid_luhn));
    }
    //endregion
}
