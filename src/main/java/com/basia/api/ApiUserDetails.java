package com.basia.api;

import com.basia.api.dto.userdetails.UserDetailsDto;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Component
@Slf4j
public class ApiUserDetails extends AbstractApi {

    @SneakyThrows
    @SuppressWarnings("all")
    public UserDetailsDto getUserDetails(String username, String token) {
        log.info("Sending user details request for user {}", username);

        Response response = given().contentType(ContentType.JSON)
                .header(new Header(AUTHORIZATION, getAuthorizationHeader(token)))
                .when()
                .get(String.format("http://localhost:4001/users/%s", username));

        return response.as(UserDetailsDto.class);
    }
}
