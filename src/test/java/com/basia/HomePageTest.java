package com.basia;

import com.basia.api.dto.register.RegisterDto;
import com.basia.config.YamlParser;
import com.basia.pages.HomePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.basia.providers.UserProvider.getRandomUser;
import static com.basia.utils.LoginUtil.loginAsRandomUser;

public class HomePageTest extends BaseTest {

    private final RegisterDto user = getRandomUser();
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
    void shouldBeOnHomePageAndGetAllUsers() {
        driver.navigate().to(YamlParser.getConfig().getUrl());

        int usersCount = apiGetAllUsers.getAllUsers(token).size();

        new HomePage(driver)
                .verifyHeaderContains(user.getFirstName())
                .verifyUserCount(usersCount);
    }
}
