package com.basia.api;


import com.basia.api.dto.userdetails.UserDetailsDto;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.restassured.RestAssured.given;

@Component
@Slf4j
public class ApiGetAllUsers extends AbstractApi {

    @SneakyThrows
    public List<UserDetailsDto> getAllUsers(String token) {
        log.info("Sending request to get all users");

        Response response = given().contentType(ContentType.JSON)
                .header(new Header(AUTHORIZATION, getAuthorizationHeader(token)))
                .when()
                .get("http://localhost:4001/users");

        return response
                .jsonPath()
                .getList(".", UserDetailsDto.class);
    }
}
