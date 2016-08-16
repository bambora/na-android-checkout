/*
 * Copyright (c) 2016 Beanstream Internet Commerce, Inc. All rights reserved.
 */

package com.beanstream.payform.models;

/**
 * Created by dlight on 2016-08-16.
 */
public class TokenResponse {

    private String token;
    private String code;
    private String version;
    private String message;

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

    public String getCode() {

        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
