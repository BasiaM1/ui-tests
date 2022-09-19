package com.basia.utils;

import com.basia.api.*;
import com.basia.api.ApiRegister;
import com.basia.api.dto.login.LoginDto;
import com.basia.api.dto.login.LoginResponseDto;
import com.basia.api.dto.register.RegisterDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginUtil {
    private static final ApiRegister apiRegister = new ApiRegister();
    private static final ApiLogin apiLogin = new ApiLogin();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static String loginAsRandomUser(RegisterDto user, WebDriver driver) {
        apiRegister.register(user);
        LoginResponseDto loginResponse = apiLogin.login(new LoginDto(user.getUsername(), user.getPassword()));
        setLocalStorage(driver, loginResponse);
        setCookie(driver, loginResponse.getToken());
        return loginResponse.getToken();
    }

    private static void setCookie(WebDriver driver, String token) {
        driver.manage().addCookie(new Cookie("token", token));
    }

    private static void setLocalStorage(WebDriver driver, LoginResponseDto loginResponse) throws JsonProcessingException {
        String userLocalStorageEntry = objectMapper.writeValueAsString(loginResponse);
        LocalStorage local = ((WebStorage) driver).getLocalStorage();
        local.setItem("user", userLocalStorageEntry);
    }
}