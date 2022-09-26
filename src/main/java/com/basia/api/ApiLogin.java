package com.basia.api;

import com.basia.api.dto.login.LoginDto;
import com.basia.api.dto.login.LoginResponseDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;


@Component
@Slf4j
public class ApiLogin extends AbstractApi {

    public LoginResponseDto login(LoginDto loginDto) {

        Response response = given().contentType(ContentType.JSON).body(loginDto)
                .when()
                .post("http://localhost:4001/users/signin");

        return response.as(LoginResponseDto.class);
    }
}
