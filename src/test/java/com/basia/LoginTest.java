package com.basia;

import com.basia.api.ApiRegister;
import com.basia.api.dto.register.RegisterDto;
import com.basia.api.dto.register.RegisterResponseDto;
import com.basia.helpers.ConstValues;
import com.basia.pages.HomePage;
import lombok.SneakyThrows;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static com.basia.config.YamlParser.getConfig;
import static com.basia.providers.UserProvider.getRandomUser;

public class LoginTest extends BaseTest {

    String token;
    RegisterDto randomUser;
    @AfterClass
    public void cleanUp() {
        apiDeleteUser.deleteUser(randomUser.getUsername(), token);
    }

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
