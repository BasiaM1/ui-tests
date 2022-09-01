package com.basia.isolation;

import com.basia.api.dto.login.FailedLoginDto;
import com.basia.api.dto.login.LoginResponseDto;
import com.basia.api.dto.register.RegisterDto;
import com.basia.pages.HomePage;
import com.basia.pages.LoginPage;
import com.google.common.net.MediaType;
import lombok.SneakyThrows;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.testng.annotations.Test;

import static com.basia.providers.UserProvider.getRandomUser;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.openqa.selenium.remote.http.Contents.utf8String;

public class IsolatedLoginTest extends IsolatedBaseTest {

    private static final String INVALID_USERNAME_PASSWORD_SUPPLIED = "Invalid username/password supplied";
    private final RegisterDto registerDto = getRandomUser();

    @Test
    public void shouldBeAbleToLogin() {
        String username = randomAlphabetic(5);
        String password = randomAlphabetic(5);
        mockSuccessfulLoginAndGetUsers(username);

        loginPage
                .attemptLogin(username, password, HomePage.class)
                .verifyHeaderContains(registerDto.getFirstName())
                .verifyUserCount(2);
    }

    @Test
    public void shouldFailToLogin() {
        mockUnsuccessfulLogin();

        loginPage
                .attemptLogin(randomAlphabetic(5), randomAlphabetic(5), LoginPage.class)
                .verifyAlertMessageContains(INVALID_USERNAME_PASSWORD_SUPPLIED);

    }

    @SuppressWarnings("all")
    private void mockSuccessfulLoginAndGetUsers(String username) {
        Route successfulLoginRoute = Route.matching(req -> req.getUri().endsWith("/users/signin"))
                .to(() -> req ->
                        new HttpResponse()
                                .setStatus(200)
                                .addHeader("Content-Type", MediaType.JSON_UTF_8.toString())
                                .setContent(utf8String(buildResponseBody(username))));

        Route getAllUsersRoute = Route.matching(req -> req.getUri().endsWith("/users"))
                .to(() -> req ->
                        new HttpResponse()
                                .setStatus(200)
                                .addHeader("Content-Type", MediaType.JSON_UTF_8.toString())
                                .setContent(utf8String(loadFile("/users.json"))));

        new NetworkInterceptor(
                driver,
                Route.combine(
                        successfulLoginRoute,
                        getAllUsersRoute)
        );
    }

    @SuppressWarnings("all")
    private void mockUnsuccessfulLogin() {
        new NetworkInterceptor(
                driver,
                Route.matching(req -> req.getUri().contains("/users/signin"))
                        .to(() -> req ->
                                new HttpResponse()
                                        .setStatus(400)
                                        .addHeader("Content-Type", MediaType.JSON_UTF_8.toString())
                                        .setContent(utf8String(buildFailedLoginResponseBody())))
        );
    }

    @SneakyThrows
    private String buildFailedLoginResponseBody() {
        FailedLoginDto failedLoginDto = FailedLoginDto.builder()
                .error("Unprocessable Entity")
                .status(422)
                .message(INVALID_USERNAME_PASSWORD_SUPPLIED)
                .path("/users/signin")
                .timestamp("2022-08-31T18:17:45.551+00:00")
                .build();

        return objectMapper.writeValueAsString(failedLoginDto);
    }

    @SneakyThrows
    private String buildResponseBody(String username) {
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .token("fakeToken")
                .roles(registerDto.getRoles())
                .email(registerDto.getEmail())
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .username(username)
                .build();

        return objectMapper.writeValueAsString(loginResponseDto);
    }

}
