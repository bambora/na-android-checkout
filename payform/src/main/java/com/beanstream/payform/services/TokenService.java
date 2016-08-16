/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.JsonReader;
import android.util.Log;

import com.beanstream.payform.activities.PayFormActivity;
import com.beanstream.payform.models.PayForm;
import com.beanstream.payform.models.Payment;
import com.beanstream.payform.models.TokenRequest;
import com.beanstream.payform.models.TokenResponse;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dlight on 2016-08-15.
 */
public class TokenService extends IntentService {
    public final static String EXTRA_RECEIVER = "com.beanstream.payform.services.receiver";
    public final static String EXTRA_TOKEN = "com.beanstream.payform.services.receiver";

    public final static String URL_TOKENIZATION = "https://www.beanstream.com/scripts/tokenization/tokens";

    public final static int SERVICE_ERROR = 22;


    public TokenService() {
        super(TokenService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RECEIVER);
        PayForm payForm = intent.getParcelableExtra(PayFormActivity.EXTRA_PAYFORM);

        Payment payment = payForm.getPayment();

        TokenRequest request = new TokenRequest();
        request.setNumber(payment.getCardNumber());
        request.setExpiryMonth(payment.getExpiryMonth());
        request.setExpiryYear(payment.getExpiryYear());
        request.setCvd(payment.getCvv());

        TokenResponse response = callTokenService(request);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TOKEN, response.getToken());

        if ((response.getHttpStatusCode() == HttpURLConnection.HTTP_OK) && (response.getCode() == TokenResponse.SUCCESS_CODE)) {
            receiver.send(Activity.RESULT_OK, bundle);
        } else {
            receiver.send(SERVICE_ERROR, bundle);
        }

    }

    private TokenResponse callTokenService(TokenRequest request) {
        TokenResponse response = new TokenResponse();
        URL url;
        HttpURLConnection connection = null;

        try {
            //Connect
            url = new URL(URL_TOKENIZATION);
            connection = (HttpURLConnection) url.openConnection();

            // Set Timeouts
            connection.setReadTimeout(10000 /* milliseconds */); //TODO
            connection.setConnectTimeout(15000 /* milliseconds */);

            // Set Headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");

            // Set Body
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(request.toJsonObject().toString());
            writer.flush();

            // Get Result
            response.setHttpStatusCode(connection.getResponseCode());
            if (response.getHttpStatusCode() == HttpURLConnection.HTTP_OK) {

                JsonReader reader = new JsonReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("token")) {
                        response.setToken(reader.nextString());
                    } else if (name.equals("code")) {
                        response.setCode(reader.nextInt());
                    } else if (name.equals("version")) {
                        response.setVersion(reader.nextString());
                    } else if (name.equals("message")) {
                        response.setMessage(reader.nextString());
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
            } else {
                Log.d("HttpResult", connection.getResponseMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }
}
