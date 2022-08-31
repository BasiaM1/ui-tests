package com.basia.api;

import com.basia.api.dto.login.LoginDto;
import com.basia.api.dto.login.LoginResponseDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class ApiLogin extends AbstractApi {

    @SneakyThrows
    public LoginResponseDto login(LoginDto loginDto) throws IOException {
        String json = objectMapper.writeValueAsString(loginDto);
        log.info(json);
        Request request = new Request.Builder()
                .url("http://localhost:4001/users/signin")
                .post(RequestBody.create(json, JSON))
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        log.info(objectMapper.readValue(responseString, LoginResponseDto.class).toString());
        return objectMapper.readValue(responseString, LoginResponseDto.class);
    }

}
