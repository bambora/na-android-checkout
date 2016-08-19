/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dlight on 2016-08-19.
 */
public class Preferences {
    private static Preferences preferences;
    private SharedPreferences sharedPreferences;

    public static String CardType = "cardType";
    public static String TokenRequestTimeoutInSeconds = "tokenRequestTimeoutInSeconds";

    public static Preferences getInstance(Context context) {
        if (preferences == null) {
            preferences = new Preferences(context);
        }
        return preferences;
    }

    private Preferences(Context context) {
        sharedPreferences = context.getSharedPreferences("com.beanstream.payform",Context.MODE_PRIVATE);
    }

    public void saveData(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }

    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }
}
