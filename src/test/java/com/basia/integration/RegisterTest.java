package com.basia.integration;

import com.basia.api.dto.login.LoginResponseDto;
import com.basia.api.dto.register.RegisterDto;
import com.basia.enums.InputFields;
import com.basia.helpers.ConstValues;
import com.basia.pages.HomePage;
import com.basia.pages.LoginPage;
import com.basia.pages.register.RegisterPage;
import com.basia.pages.register.RegisterPageValidator;
import com.basia.providers.UserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    RegisterPage registerPage;
    RegisterPageValidator validator;
    RegisterDto newUser;
    String token;

    @BeforeMethod
    private void setUpPageAndValidator() {
        loginPage = new LoginPage(localDriverManager);
    }

    @AfterClass
    public void cleanUp() {
        apiDeleteUser.deleteUser(newUser.getUsername(), token);
    }

    @Test
    public void shouldBeAbleToRegisterNewUserAndLogin() {
        newUser = UserProvider.getRandomUser();
        registerNewUser(newUser)
                .verifyAlertMessageContains(ConstValues.REGISTER_MESSAGE)
                .attemptLogin(newUser.getUsername(), newUser.getPassword(), HomePage.class)
                .verifyHeaderContains(newUser.getFirstName());

        token = getTokenForLoggedUser();
    }

    @Test
    public void shouldNotBeAbleToRegisterUserThatExists() {
        final RegisterDto randomUser = UserProvider.getRandomUser();
        registerNewUser(randomUser);
        loginPage
                .goToRegisterPage()
                .attemptRegister(randomUser, RegisterPage.class)
                .verifyAlertMessageContains(ConstValues.FAIL_REGISTER_MESSAGE);
    }

    @Test
    public void shouldFailToRegisterWithTooShortData() {
        registerPage = loginPage.goToRegisterPage();
        validator = new RegisterPageValidator(registerPage);

        registerPage.attemptRegister(UserProvider.getUserWithTooShortData(), RegisterPage.class);

        InputFields.getRegisterInputLabels().forEach(validator::assertErrorMessageForFieldIsDisplayed);

        InputFields.getRegisterInputLabels().forEach(validator::assertFieldHasRedBorder);
    }

    private LoginPage registerNewUser(RegisterDto user) {
        return loginPage
                .goToRegisterPage()
                .attemptRegister(user);
    }

    @SneakyThrows
    private String getTokenForLoggedUser() {
        LocalStorage local = ((WebStorage) localDriverManager.getDriver()).getLocalStorage();
        String userDetails = local.getItem("user");
        ObjectMapper mapper = new ObjectMapper();
        LoginResponseDto loginResponseDto = mapper.readValue(userDetails, LoginResponseDto.class);
        return loginResponseDto.getToken();

    }
}
