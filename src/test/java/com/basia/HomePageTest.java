package com.basia;

import com.basia.api.dto.register.RegisterDto;
import com.basia.config.YamlParser;
import com.basia.pages.HomePage;
import com.basia.utils.LoginUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.basia.providers.UserProvider.getRandomUser;
import static com.basia.utils.LoginUtil.loginAsRandomUser;

public class HomePageTest extends BaseTest {

    private final RegisterDto user = getRandomUser();

    @BeforeMethod
    public void login() {
        loginAsRandomUser(user, driver);
    }

    @Test
    void shouldBeOnHomePage() {
        driver.navigate().to(YamlParser.getConfig().getUrl());
        new HomePage(driver)
                .verifyHeaderContains(user.getFirstName())
                .verifyUserCount(2);
    }
}
