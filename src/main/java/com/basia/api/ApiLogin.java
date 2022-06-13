package com.basia.api;

import com.basia.AbstractApi;
import com.basia.api.dto.LoginDto;
import com.basia.api.dto.LoginResponseDto;
import okhttp3.*;

import java.io.IOException;

public class ApiLogin extends AbstractApi {

    public LoginResponseDto login(LoginDto loginDto) throws IOException {
        String json = objectMapper.writeValueAsString(loginDto);
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://localhost:4001/users/signin")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseString = response.body().string();
            return objectMapper.readValue(responseString, LoginResponseDto.class);
        }
    }

}
