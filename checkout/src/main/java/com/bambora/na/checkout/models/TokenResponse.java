/*
 * Copyright (c) 2017 Bambora
 */

package com.bambora.na.checkout.models;

/**
 * Created by dlight on 2016-08-16.
 */
public class TokenResponse {

    public final static int SUCCESS_CODE = 1;

    private String token;

    private int httpStatusCode;

    private int code;
    private String message;
    private String version;

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {

        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
