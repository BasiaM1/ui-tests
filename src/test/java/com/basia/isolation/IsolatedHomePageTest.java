package com.basia.isolation;

import com.basia.api.dto.login.LoginResponseDto;
import com.basia.api.dto.register.RegisterDto;
import com.basia.api.dto.userdetails.UserDetailsDto;
import com.basia.pages.EditPage;
import com.basia.pages.HomePage;
import com.basia.routes.DeleteUserRoute;
import com.basia.routes.EditUserRoute;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.http.Routable;
import org.openqa.selenium.remote.http.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.basia.enums.InputFields.FIRST_NAME;
import static com.basia.enums.InputFields.LAST_NAME;
import static com.basia.providers.UserProvider.getRandomUser;
import static com.basia.routes.GetAllUsersRoutes.successfulGetAllUsersRoute;
import static com.basia.routes.GetAllUsersRoutes.successfulGetAllUsersRouteWithLoggedUser;

public class IsolatedHomePageTest extends IsolatedBaseTest {
    RegisterDto user = getRandomUser();
    @Autowired
    HomePage homePage;
    @Autowired
    private EditPage editPage;
    private UserDetailsDto userDetails;


    @BeforeMethod
    public void loginUser() {
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

        userDetails = UserDetailsDto.builder()
                .id(9)
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    @SneakyThrows
    @Test
    public void shouldDeleteUsers() {
        mockGetUsers();
        driver.navigate().to(config.getUrl());
        mockDeleteUsers();

        List<String> actualUsersList = homePage.getUsers().stream()
                .map(WebElement::getText)
                .map(el -> el.split(" -")[0])
                .collect(Collectors.toList());

        String[] expectedUsers = getUsersList().stream()
                .map(u -> String.format("%s %s", u.getFirstName(), u.getLastName()))
                .toArray(String[]::new);

        Assertions.assertThat(actualUsersList).containsExactlyInAnyOrder(expectedUsers);

        homePage
                .deleteAllUsersWithoutDefault("fsdfdsfdf")
                .verifyUserCount(2);
    }

    @Test
    public void shouldEditUser() {
        mockGetUsersWithLoggedUser(userDetails);
        driver.navigate().to(config.getUrl());
        mockSuccessfulEditUser(userDetails);
        homePage.goToEditUserDetails1();
        String newFirstName = RandomStringUtils.randomAlphabetic(8);
        String newLastName = RandomStringUtils.randomAlphabetic(8);
        editPage.editUserDetails(FIRST_NAME.getLabel(), newFirstName);
        editPage.editUserDetails(LAST_NAME.getLabel(), newLastName);

        editPage.getEditUserButton().click();


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

    private void mockGetUsersWithLoggedUser(UserDetailsDto userDetailsDto) {
        new NetworkInterceptor(
                driver,
                Route.combine(
                        successfulGetAllUsersRouteWithLoggedUser(userDetailsDto)
                )
        );
    }

    private void mockSuccessfulEditUser(UserDetailsDto userDetailsDto) {
        new NetworkInterceptor(
                driver,
                Route.combine(
                        EditUserRoute.successfullyEditUser(userDetailsDto.getUsername())));
    }

}
