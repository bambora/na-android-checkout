/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform;

import com.beanstream.payform.models.CardType;
import com.beanstream.payform.models.CreditCard;
import com.beanstream.payform.validators.CreditCardValidator;

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
@Config(constants = BuildConfig.class)
public class CreditCardValidatorTest {
    public final static String cardnumber_luhn_valid = "1234 5678 9012 3452";

    public final static String expiry_month_valid = "12";
    public final static String expiry_year_valid = "20";

    public final static String amex_valid = "3782 8224 6310 005";
    public final static String amex_invalid_cardtype = "328282246310005";
    public final static String amex_invalid_number = "37828224631000555";
    public final static String amex_invalid_luhn = "378282246310015";

    public final static String discover_valid = "6011 0009 9013 9424";
    public final static String discover_invalid_cardtype = "5011000990139424";
    public final static String discover_invalid_number = "60110009901394244";
    public final static String discover_invalid_luhn = "6011000990139434";

    public final static String diners_valid = "3852 0000 0232 37";
    public final static String diners_invalid_cardtype = "30620000023237";
    public final static String diners_invalid_number = "385200000232377";
    public final static String diners_invalid_luhn = "38520000023247";

    public final static String mastercard_valid = "5555 5555 5555 4444";
    public final static String mastercard_invalid_cardtype = "5655 5555 5555 4444";
    public final static String mastercard_invalid_number = "5555 5555 5555 44444";
    public final static String mastercard_invalid_luhn = "5555 5555 5555 4443";

    public final static String mastercard_newbin_valid = "2221 0000 0000 0009";
    public final static String mastercard_newbin_invalid_number = "2221 0000 0000 00090";
    public final static String mastercard_newbin_invalid_luhn = "2720 9999 9999 9999";

    public final static String visa_valid = "4012 8888 8888 1881";
    public final static String visa_invalid_cardtype = "1012888888881881";
    public final static String visa_invalid_number = "3012888888881881";
    public final static String visa_invalid_luhn = "4012888888882881";

    //region isValidCardNumber
    @Test
    public void cardValidator_CardNumber_Correct_ReturnsTrue() {
        assertTrue(CreditCardValidator.isValidCardNumber(amex_valid, CardType.AMEX));
        assertTrue(CreditCardValidator.isValidCardNumber(diners_valid, CardType.DINERS));
        assertTrue(CreditCardValidator.isValidCardNumber(discover_valid, CardType.DISCOVER));
        assertTrue(CreditCardValidator.isValidCardNumber(mastercard_valid, CardType.MASTERCARD));
        assertTrue(CreditCardValidator.isValidCardNumber(mastercard_newbin_valid, CardType.MASTERCARD));
        assertTrue(CreditCardValidator.isValidCardNumber(visa_valid, CardType.VISA));
    }

    @Test
    public void cardValidator_CardNumber_Empty_ReturnsFalse() {
        assertFalse("empty", CreditCardValidator.isValidCardNumber("", CardType.VISA));
        assertFalse("whitespace", CreditCardValidator.isValidCardNumber(" ", CardType.VISA));
        assertFalse("null", CreditCardValidator.isValidCardNumber(null, CardType.VISA));

        assertFalse("empty", CreditCardValidator.isValidCardNumber(amex_valid, ""));
        assertFalse("whitespace", CreditCardValidator.isValidCardNumber(amex_valid, " "));
        assertFalse("null", CreditCardValidator.isValidCardNumber(amex_valid, null));
    }

    @Test
    public void cardValidator_CardNumber_Invalid_ReturnsFalse() {
        assertFalse(CreditCardValidator.isValidCardNumber(amex_invalid_number, CardType.AMEX));
        assertFalse(CreditCardValidator.isValidCardNumber(diners_invalid_number, CardType.DINERS));
        assertFalse(CreditCardValidator.isValidCardNumber(discover_invalid_number, CardType.DISCOVER));
        assertFalse(CreditCardValidator.isValidCardNumber(mastercard_invalid_number, CardType.MASTERCARD));
        assertFalse(CreditCardValidator.isValidCardNumber(mastercard_newbin_invalid_number, CardType.MASTERCARD));
        assertFalse(CreditCardValidator.isValidCardNumber(visa_invalid_number, CardType.VISA));

        assertFalse(CreditCardValidator.isValidCardNumber(amex_valid + "1", CardType.AMEX));
        assertFalse(CreditCardValidator.isValidCardNumber(diners_valid + "1", CardType.DINERS));
        assertFalse(CreditCardValidator.isValidCardNumber(discover_valid + "1", CardType.DISCOVER));
        assertFalse(CreditCardValidator.isValidCardNumber(mastercard_valid + "1", CardType.MASTERCARD));
        assertFalse(CreditCardValidator.isValidCardNumber(visa_valid + "1", CardType.VISA));
    }
    //endregion

    //region isValidCardType

    @Test
    public void cardValidator_CardType_Correct_ReturnsTrue() {
        assertTrue(CreditCardValidator.isValidCardType(amex_valid));
        assertTrue(CreditCardValidator.isValidCardType(diners_valid));
        assertTrue(CreditCardValidator.isValidCardType(discover_valid));
        assertTrue(CreditCardValidator.isValidCardType(mastercard_valid));
        assertTrue(CreditCardValidator.isValidCardType(mastercard_newbin_valid));
        assertTrue(CreditCardValidator.isValidCardType(visa_valid));
    }

    @Test
    public void cardValidator_CardType_Empty_ReturnsFalse() {
        assertFalse("empty", CreditCardValidator.isValidCardType(""));
        assertFalse("whitespace", CreditCardValidator.isValidCardType(" "));
        assertFalse("null", CreditCardValidator.isValidCardType(null));
    }

    @Test
    public void cardValidator_CardType_Invalid_ReturnsFalse() {
        assertFalse("amex", CreditCardValidator.isValidCardType(amex_invalid_cardtype));
        assertFalse("diners", CreditCardValidator.isValidCardType(diners_invalid_cardtype));
        assertFalse("discover", CreditCardValidator.isValidCardType(discover_invalid_cardtype));
        assertFalse("mastercard", CreditCardValidator.isValidCardType(mastercard_invalid_cardtype));
        assertFalse("visa", CreditCardValidator.isValidCardType(visa_invalid_cardtype));
    }
    //endregion

    //region isValidLuhn
    @Test
    public void cardValidator_Luhn_Correct_ReturnsTrue() {
        assertTrue(CreditCardValidator.isValidLuhn(cardnumber_luhn_valid));

        assertTrue(CreditCardValidator.isValidLuhn(amex_valid));
        assertTrue(CreditCardValidator.isValidLuhn(diners_valid));
        assertTrue(CreditCardValidator.isValidLuhn(discover_valid));
        assertTrue(CreditCardValidator.isValidLuhn(mastercard_valid));
        assertTrue(CreditCardValidator.isValidLuhn(mastercard_newbin_valid));
        assertTrue(CreditCardValidator.isValidLuhn(visa_valid));
    }

    @Test
    public void cardValidator_Luhn_Empty_ReturnsFalse() {
        assertFalse("empty", CreditCardValidator.isValidLuhn(""));
        assertFalse("whitespace", CreditCardValidator.isValidLuhn(" "));
        assertFalse("null", CreditCardValidator.isValidLuhn(null));
    }

    @Test
    public void cardValidator_Luhn_Invalid_ReturnsFalse() {
        assertFalse("amex", CreditCardValidator.isValidLuhn(amex_invalid_luhn));
        assertFalse("discover", CreditCardValidator.isValidLuhn(discover_invalid_luhn));
        assertFalse("diners", CreditCardValidator.isValidLuhn(diners_invalid_luhn));
        assertFalse("mastercard", CreditCardValidator.isValidLuhn(mastercard_invalid_luhn));
        assertFalse("mastercard new bin", CreditCardValidator.isValidLuhn(mastercard_newbin_invalid_luhn));
        assertFalse("visa", CreditCardValidator.isValidLuhn(visa_invalid_luhn));
    }
    //endregion

    //region isValidCard
    @Test
    public void cardValidator_Card_Correct_ReturnsTrue() {
        assertTrue(CreditCardValidator.isValidCard(new CreditCard(amex_valid, expiry_month_valid, expiry_year_valid, CvvValidatorTest.cvv_valid_amex)));
        assertTrue(CreditCardValidator.isValidCard(new CreditCard(diners_valid, expiry_month_valid, expiry_year_valid, CvvValidatorTest.cvv_valid)));
        assertTrue(CreditCardValidator.isValidCard(new CreditCard(discover_valid, expiry_month_valid, expiry_year_valid, CvvValidatorTest.cvv_valid)));
        assertTrue(CreditCardValidator.isValidCard(new CreditCard(mastercard_valid, expiry_month_valid, expiry_year_valid, CvvValidatorTest.cvv_valid)));
        assertTrue(CreditCardValidator.isValidCard(new CreditCard(visa_valid, expiry_month_valid, expiry_year_valid, CvvValidatorTest.cvv_valid)));
    }

    @Test
    public void cardValidator_Card_Empty_ReturnsFalse() {
        assertFalse("null", CreditCardValidator.isValidCard(null));
    }

    @Test
    public void cardValidator_Card_Invalid_ReturnsFalse() {
        assertFalse(CreditCardValidator.isValidCard(new CreditCard(visa_invalid_number, expiry_month_valid, expiry_year_valid, CvvValidatorTest.cvv_valid)));
        assertFalse(CreditCardValidator.isValidCard(new CreditCard(visa_valid, expiry_month_valid, expiry_year_valid, CvvValidatorTest.cvv_invalid_short)));
    }
    //endregion
}
