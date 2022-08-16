package com.basia.api;

import com.basia.AbstractApi;
import com.basia.api.dto.userdetails.UserDetailsDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
public class ApiUserDetails extends AbstractApi {

    @SneakyThrows
    @SuppressWarnings("all")
    public UserDetailsDto getUserDetails(String username, String token) {
        log.info("Sending user details request for user {}", username);
        Request request = new Request.Builder()
                .url(String.format("http://localhost:4001/users/%s", username))
                .get()
                .addHeader(AUTHORIZARION, getAuthorizationHeader(token))
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        log.info("Request response {}", responseString);
        return objectMapper.readValue(responseString, UserDetailsDto.class);
    }

}
