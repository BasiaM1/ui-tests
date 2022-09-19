package com.basia.isolation;

import com.basia.api.dto.login.LoginResponseDto;
import com.basia.api.dto.register.RegisterDto;
import com.basia.api.dto.userdetails.UserDetailsDto;
import com.basia.config.YamlParser;
import com.basia.pages.HomePage;
import com.basia.routes.DeleteUserRoute;
import lombok.SneakyThrows;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.http.Routable;
import org.openqa.selenium.remote.http.Route;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.basia.providers.UserProvider.getRandomUser;
import static com.basia.routes.GetAllUsersRoutes.successfulGetAllUsersRoute;

public class IsolatedHomePageTest extends IsolatedBaseTest {

    @BeforeMethod
    public void loginUser() {
        RegisterDto user = getRandomUser();

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .token("fakeToken")
                .build();

        setCookie(driver, loginResponseDto.getToken());
        setLocalStorage(driver, loginResponseDto);
        mockGetUsers();
        driver.navigate().to(YamlParser.getConfig().getUrl());
    }

    @SneakyThrows
    @Test
    public void shouldDeleteUsers() {
        mockDeleteUsers();

        new HomePage(driver)
                .deleteAllUsersWithoutDefault("fsdfdsfdf")
                .verifyUserCount(2);
    }

    @SuppressWarnings("resource")
    private void mockDeleteUsers() {
        new NetworkInterceptor(
                driver,
                Route.combine(
                        generateListOfDeleteRoutes()
                )
        );
    }

    private List<Routable> generateListOfDeleteRoutes() {
        return getUsersList().stream()
                .filter(it -> !it.getFirstName().contains("Slawomir") && !it.getFirstName().contains("Gosia"))
                .map(UserDetailsDto::getUsername)
                .map(DeleteUserRoute::successfullyDeleteUser)
                .collect(Collectors.toList());
    }

    private void setCookie(WebDriver driver, String token) {
        driver.manage().addCookie(new Cookie("token", token));
    }

    @SneakyThrows
    private void setLocalStorage(WebDriver driver, LoginResponseDto loginResponse) {
        String userLocalStorageEntry = objectMapper.writeValueAsString(loginResponse);
        LocalStorage local = ((WebStorage) driver).getLocalStorage();
        local.setItem("user", userLocalStorageEntry);
    }

    @SuppressWarnings("resource")
    private void mockGetUsers() {
        new NetworkInterceptor(
                driver,
                Route.combine(
                        successfulGetAllUsersRoute()
                )
        );
    }


}
