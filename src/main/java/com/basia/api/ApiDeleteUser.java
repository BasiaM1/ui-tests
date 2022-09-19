package com.basia.api;

import io.restassured.http.Header;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static io.restassured.RestAssured.given;

@Slf4j
public class ApiDeleteUser extends AbstractApi {

    public void deleteUser(String username, String token) {
        log.info("Sending delete request for user {}", username);

        Response response = given()
                .header(new Header(AUTHORIZATION, getAuthorizationHeader(token)))
                .delete(String.format("http://localhost:4001/users/%s", username));

        log.info("Delete user request executed with status code {}", response.statusCode());
    }
}
