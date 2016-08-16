/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.beanstream.payform.activities.PayFormActivity;
import com.beanstream.payform.models.PayForm;

/**
 * Created by dlight on 2016-08-15.
 */
public class TokenService extends IntentService {
    public final static String EXTRA_RECEIVER = "com.beanstream.payform.services.receiver";

    public TokenService() {
        super(TokenService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RECEIVER);
        PayForm payForm = intent.getParcelableExtra(PayFormActivity.EXTRA_PAYFORM);

        Bundle bundle = new Bundle();

        //TODO call tokenization service
        bundle.putString("resultValue", "My Result Value. Passed in: " + payForm.getPayment().getName());

        receiver.send(Activity.RESULT_OK, bundle);
    }
}
