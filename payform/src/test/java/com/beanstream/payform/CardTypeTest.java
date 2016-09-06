/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform;

/**
 * Created by dlight on 2016-08-18.
 */
/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

import com.beanstream.payform.models.CardType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Created by dlight on 2016-08-17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CardTypeTest {

    //region getCardTypeFromCardNumber
    @Test
    public void cardValidator_getCardType_Correct_ReturnsCardType() {
        assertEquals(CardType.AMEX, CardType.getCardTypeFromCardNumber(CardNumberValidatorTest.amex_valid));
        assertEquals(CardType.DINERS, CardType.getCardTypeFromCardNumber(CardNumberValidatorTest.diners_valid));
        assertEquals(CardType.DISCOVER, CardType.getCardTypeFromCardNumber(CardNumberValidatorTest.discover_valid));
        assertEquals(CardType.MASTERCARD, CardType.getCardTypeFromCardNumber(CardNumberValidatorTest.mastercard_valid));
        assertEquals(CardType.MASTERCARD, CardType.getCardTypeFromCardNumber(CardNumberValidatorTest.mastercard_newbin_valid));
        assertEquals(CardType.VISA, CardType.getCardTypeFromCardNumber(CardNumberValidatorTest.visa_valid));
    }

    @Test
    public void cardValidator_getCardType_Correct_ReturnsInvalidCardType() {
        assertEquals(CardType.INVALID, CardType.getCardTypeFromCardNumber(CardNumberValidatorTest.amex_invalid_cardtype));
        assertEquals(CardType.INVALID, CardType.getCardTypeFromCardNumber(CardNumberValidatorTest.diners_invalid_cardtype));
        assertEquals(CardType.INVALID, CardType.getCardTypeFromCardNumber(CardNumberValidatorTest.discover_invalid_cardtype));
        assertEquals(CardType.INVALID, CardType.getCardTypeFromCardNumber(CardNumberValidatorTest.mastercard_invalid_cardtype));
        assertEquals(CardType.INVALID, CardType.getCardTypeFromCardNumber(CardNumberValidatorTest.visa_invalid_cardtype));
    }
    //endregion
}
