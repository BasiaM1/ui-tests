package com.basia.api;

public abstract class AbstractApi {

    protected static final String AUTHORIZATION = "Authorization";

    protected String getAuthorizationHeader(String token) {
        return String.format("Bearer %s", token);
    }

}
