package com.basia.api;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
public class ApiDeleteUser extends AbstractApi {

    @SneakyThrows
    public void deleteUser(String username, String token) {
        log.info("Sending delete request for user {}", username);
        Request request = new Request.Builder()
                .url(String.format("http://localhost:4001/users/%s", username))
                .delete()
                .addHeader(AUTHORIZATION, getAuthorizationHeader(token))
                .build();

        log.info("Executing query to delete user {}", username);
        Response response = client.newCall(request).execute();
        log.info("Delete user request executed with status code {}", response.code());
    }
}
