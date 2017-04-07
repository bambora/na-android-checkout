/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.na.checkout;

import android.os.Build;

import com.bambora.na.checkout.validators.ExpiryValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by dlight on 2016-09-06.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class ExpiryValidatorTest {
    //region isValidExpiry
    @Test
    public void expiryValidator_isValidExpiry_Correct_ReturnsTrue() {
        assertTrue(ExpiryValidator.isValidExpiry("11"));
        assertTrue(ExpiryValidator.isValidExpiry("2106"));
        assertTrue(ExpiryValidator.isValidExpiry("01"));
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
