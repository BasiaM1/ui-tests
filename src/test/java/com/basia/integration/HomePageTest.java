package com.basia.integration;

import com.basia.api.ApiDeleteUser;
import com.basia.api.ApiGetAllUsers;
import com.basia.api.ApiRegister;
import com.basia.api.dto.register.RegisterDto;
import com.basia.config.YamlParser;
import com.basia.pages.HomePage;
import com.basia.utils.LoginUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.basia.providers.UserProvider.getRandomUser;

public class HomePageTest extends BaseTest {

    private final RegisterDto user = getRandomUser();
    private final RegisterDto user1 = getRandomUser();
    private final RegisterDto user2 = getRandomUser();
    @Autowired
    private ApiGetAllUsers apiGetAllUsers;
    @Autowired
    private ApiDeleteUser apiDeleteUser;
    @Autowired
    private ApiRegister apiRegister;
    @Autowired
    private LoginUtil loginUtil;

    private String token;

    @BeforeMethod
    public void login() {
        token = loginUtil.loginAsRandomUser(user, driver);
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
        apiRegister.register(user1);
        apiRegister.register(user2);

        driver.navigate().to(YamlParser.getConfig().getUrl());

        int defaultUsersSizeAndLogged = 3;

        new HomePage(driver)
                .deleteAllUsersWithoutDefault(user.getFirstName())
                .verifyUserCount(defaultUsersSizeAndLogged);
    }
}
