package com.basia;

import com.basia.api.ApiLogin;
import com.basia.api.dto.LoginDto;
import com.basia.api.dto.LoginResponseDto;
import com.basia.pages.HomePage;
import com.basia.pages.LoginPage;
import lombok.SneakyThrows;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.basia.config.YamlParser.getConfig;

public class LoginTest extends BaseTest {

    @BeforeMethod
    private void goToLoginPage() {
        driver.navigate().to(getConfig().getUrl());
    }

    @Test
    public void shouldBeAbleToLoginAsAdmin() {
        new LoginPage(driver)
                .attemptLogin(getConfig().getAdminUsername(), getConfig().getAdminPassword(), HomePage.class)
                .verifyHeaderContains("Slawomir");
    }

    @SneakyThrows
    @Test
    public void shouldBeAbleToLoginAsNewlyGeneratedUser() {
        ApiLogin apiRegister = new ApiLogin();
        LoginResponseDto loginResponseDto = apiRegister.login(new LoginDto("admin", "admin"));

        new LoginPage(driver)
                .attemptLogin(getConfig().getAdminUsername(), getConfig().getAdminPassword(), HomePage.class)
                .verifyHeaderContains("Slawomir");
    }

    @Test
    public void shouldFailToLogin() {
        new LoginPage(driver)
                .attemptLogin("wrong", "wrong")
                .verifyAlertMessageContains("Invalid username/password supplied");
    }
}
