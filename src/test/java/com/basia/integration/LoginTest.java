package com.basia.integration;

import com.basia.api.dto.register.RegisterDto;
import com.basia.api.dto.register.RegisterResponseDto;
import com.basia.helpers.ConstValues;
import com.basia.pages.HomePage;
import lombok.SneakyThrows;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.basia.providers.UserProvider.getRandomUser;

public class LoginTest extends BaseTest {

    String token;
    RegisterDto randomUser = getRandomUser();

    @AfterClass
    public void cleanUp() {
        apiDeleteUser.deleteUser(randomUser.getUsername(), token);
    }

    @Test
    public void shouldBeAbleToLoginAsAdmin() {
        loginPage
                .attemptLogin(config.getAdminUsername(), config.getAdminPassword(), HomePage.class)
                .verifyHeaderContains("Slawomir");
    }

    @SneakyThrows
    @Test
    public void shouldBeAbleToLoginAsNewlyGeneratedUser() {
        randomUser = getRandomUser();
        RegisterResponseDto registerUser = apiRegister.register(randomUser);
        token = registerUser.getToken();

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
