package com.basia;

import com.basia.config.YamlParser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @BeforeMethod
    private void goToLoginPage() {
        driver.navigate().to(YamlParser.getConfig().getUrl());
    }

    @Test
    public void shouldBeAbleToLogin() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.attemptLogin("admin", "admin");
    }
}
