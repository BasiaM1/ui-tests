package com.basia.integration;

import com.basia.api.ApiDeleteUser;
import com.basia.api.ApiGetAllUsers;
import com.basia.api.ApiRegister;
import com.basia.api.dto.register.RegisterDto;
import com.basia.config.YamlParser;
import com.basia.pages.HomePage;
import lombok.SneakyThrows;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.basia.providers.UserProvider.getRandomUser;
import static com.basia.utils.LoginUtil.loginAsRandomUser;

public class HomePageTest extends BaseTest {

    private final RegisterDto user = getRandomUser();
    private final RegisterDto user1 = getRandomUser();
    private final RegisterDto user2 = getRandomUser();
    private final ApiGetAllUsers apiGetAllUsers = new ApiGetAllUsers();
    private final ApiDeleteUser apiDeleteUser = new ApiDeleteUser();

    private String token;

    @BeforeMethod
    public void login() {
        token = loginAsRandomUser(user, driver);
    }

    @AfterMethod
    public void cleanUp() {
        apiDeleteUser.deleteUser(user.getUsername(), token);
    }

    @Test
    public void shouldBeOnHomePageAndGetAllUsers() {
        driver.navigate().to(YamlParser.getConfig().getUrl());

        int usersCount = apiGetAllUsers.getAllUsers(token).size();

        new HomePage(driver)
                .verifyHeaderContains(user.getFirstName())
                .verifyUserCount(usersCount);
    }

    @SneakyThrows
    @Test
    public void shouldBeAbleToDeleteUsers() {
        ApiRegister apiRegister = new ApiRegister();
        apiRegister.register(user1);
        apiRegister.register(user2);

        driver.navigate().to(YamlParser.getConfig().getUrl());

        int defaultUsersSizeAndLogged = 3;

        new HomePage(driver)
                .deleteAllUsersWithoutDefault(user.getFirstName())
                .verifyUserCount(defaultUsersSizeAndLogged);
    }
}
