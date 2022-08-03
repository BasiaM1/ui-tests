package com.basia;

import com.basia.api.ApiRegister;
import com.basia.api.dto.register.RegisterDto;
import com.basia.helpers.ConstValues;
import com.basia.pages.HomePage;
import lombok.SneakyThrows;
import org.testng.annotations.Test;

import static com.basia.config.YamlParser.getConfig;
import static com.basia.providers.UserProvider.getRandomUser;

public class LoginTest extends BaseTest {

    @Test
    public void shouldBeAbleToLoginAsAdmin() {
        loginPage
                .attemptLogin(getConfig().getAdminUsername(), getConfig().getAdminPassword(), HomePage.class)
                .verifyHeaderContains("Slawomir");
    }

    @SneakyThrows
    @Test
    public void shouldBeAbleToLoginAsNewlyGeneratedUser() {
        ApiRegister apiRegister = new ApiRegister();
        RegisterDto randomUser = getRandomUser();
        apiRegister.register(randomUser);

        loginPage
                .attemptLogin(randomUser.getUsername(), randomUser.getPassword(), HomePage.class)
                .verifyHeaderContains(randomUser.getFirstName());
    }

    @Test
    public void shouldFailToLogin() {
        loginPage
                .attemptLogin("wrong", "wrong")
                .verifyAlertMessageContains(ConstValues.FAIL_LOGIN_MESSAGE);
    }
}
