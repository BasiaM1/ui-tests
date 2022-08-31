package com.basia.api;


import com.basia.api.dto.userdetails.UserDetailsDto;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ApiGetAllUsers extends AbstractApi {

    @SneakyThrows
    public List<UserDetailsDto> getAllUsers(String token) {
        log.info("Sending request to get all users");

        Request request = new Request.Builder()
                .url("http://localhost:4001/users")
                .get()
                .addHeader(AUTHORIZATION, getAuthorizationHeader(token))
                .build();

        ResponseBody response = client.newCall(request).execute().body();

        Gson gson = new Gson();
        Type userDetails = new TypeToken<ArrayList<UserDetailsDto>>() {
        }.getType();
        return gson.fromJson(response.string(), userDetails);

//        log.info("Request response {}", responseString);
//        return objectMapper.readValue(response.string(), new TypeReference<List<UserDetailsDto>>() {
//        });
    }
}
