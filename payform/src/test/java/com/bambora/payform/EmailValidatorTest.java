/*
 * Copyright (c) 2017 Bambora
 *
 * Created by dlight on 2016-08-17.
 *
 */

package com.bambora.payform;

import android.os.Build;

import com.bambora.payform.validators.EmailValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class EmailValidatorTest {

    @Test
    public void emailValidator_isValidEmail_Correct_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.com"));
    }

    @Test
    public void emailValidator_isValidEmail_Empty_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(""));
        assertFalse(EmailValidator.isValidEmail(" "));
        assertFalse(EmailValidator.isValidEmail(null));
    }

    @Test
    public void emailValidator_isValidEmail_Incorrect_ReturnsFalse() {
        assertFalse("no @", EmailValidator.isValidEmail("nameemail.com"));
        assertFalse("no domain or com", EmailValidator.isValidEmail("name@"));
        assertFalse("no domain", EmailValidator.isValidEmail("name@.com"));
        assertFalse("no @ and domain", EmailValidator.isValidEmail("name"));
        assertFalse("no .com", EmailValidator.isValidEmail("name@email"));
        assertFalse("no com ", EmailValidator.isValidEmail("name@email."));
        assertFalse("no name and @", EmailValidator.isValidEmail("email.com"));
        assertFalse("no name", EmailValidator.isValidEmail("@email.com"));
    }
}
