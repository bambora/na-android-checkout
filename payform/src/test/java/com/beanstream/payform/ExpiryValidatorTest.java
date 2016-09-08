/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform;

import com.beanstream.payform.validators.ExpiryValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by dlight on 2016-09-06.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExpiryValidatorTest {

    //region formatExpiry
    @Test
    public void expiryValidator_formatExpiry_Correct_ReturnsExpected() {
        assertEquals("11/12", ExpiryValidator.formatExpiry("11/12"));
        assertEquals("11/12", ExpiryValidator.formatExpiry(" 11/12"));
        assertEquals("11/12", ExpiryValidator.formatExpiry("11/12 "));
        assertEquals("11/12", ExpiryValidator.formatExpiry("1112"));
        assertEquals("11/12", ExpiryValidator.formatExpiry("1112 "));
        assertEquals("11/12", ExpiryValidator.formatExpiry("11/12 "));
        assertEquals("11/12", ExpiryValidator.formatExpiry(" 1112"));
        assertEquals("11/12", ExpiryValidator.formatExpiry("111 2"));
        assertEquals("11/12", ExpiryValidator.formatExpiry("1112abc"));
    }

    @Test
    public void expiryValidator_formatExpiry_Empty_ReturnsExpected() {
        assertEquals("", ExpiryValidator.formatExpiry(null));
        assertEquals("", ExpiryValidator.formatExpiry(""));
        assertEquals("", ExpiryValidator.formatExpiry(" "));
    }

    //region isValidExpiry
    @Test
    public void expiryValidator_isValidExpiry_Correct_ReturnsTrue() {
        assertTrue(ExpiryValidator.isValidExpiry("11/22"));
        assertTrue(ExpiryValidator.isValidExpiry("01/22"));
        assertTrue(ExpiryValidator.isValidExpiry("12/22"));
    }

    @Test
    public void expiryValidator_isValidExpiry_Incorrect_ReturnsFalse() {
        assertFalse(ExpiryValidator.isValidExpiry("00/12"));
        assertFalse(ExpiryValidator.isValidExpiry("/"));
        assertFalse(ExpiryValidator.isValidExpiry(""));
        assertFalse(ExpiryValidator.isValidExpiry(null));
        assertFalse(ExpiryValidator.isValidExpiry("11/12"));
        assertFalse(ExpiryValidator.isValidExpiry("00/22"));
        assertFalse(ExpiryValidator.isValidExpiry("1/22"));
        assertFalse(ExpiryValidator.isValidExpiry("13/22"));
        assertFalse(ExpiryValidator.isValidExpiry("11/2"));
        assertFalse(ExpiryValidator.isValidExpiry("/12"));
        assertFalse(ExpiryValidator.isValidExpiry("11/"));
    }
    //endRegion
}
