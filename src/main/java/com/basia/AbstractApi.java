package com.basia;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public abstract class AbstractApi {

    protected static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    protected OkHttpClient client = new OkHttpClient();
    protected ObjectMapper objectMapper = new ObjectMapper();

}
