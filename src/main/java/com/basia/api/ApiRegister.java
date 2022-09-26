package com.basia.api;

import com.basia.api.dto.register.RegisterDto;
import com.basia.api.dto.register.RegisterResponseDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@Component
@Slf4j
public class ApiRegister extends AbstractApi {

    public RegisterResponseDto register(RegisterDto registerDto) throws IOException {

        Response response = given().body(registerDto).contentType(ContentType.JSON)
                .when().post("http://localhost:4001/users/signup");

        return response.as(RegisterResponseDto.class);
    }

}
