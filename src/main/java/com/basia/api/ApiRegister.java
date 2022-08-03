package com.basia.api;

import com.basia.AbstractApi;
import com.basia.api.dto.register.RegisterDto;
import com.basia.api.dto.register.RegisterResponseDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
public class ApiRegister extends AbstractApi {

    public RegisterResponseDto register(RegisterDto registerDto) throws IOException {
        String requestBody = objectMapper.writeValueAsString(registerDto);
        log.info("Sending request {}", requestBody);
        Request request = new Request.Builder()
                .url("http://localhost:4001/users/signup")
                .post(RequestBody.create(requestBody, JSON))
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        log.info("Request response {}", responseString);
        return objectMapper.readValue(responseString, RegisterResponseDto.class);
    }

}
