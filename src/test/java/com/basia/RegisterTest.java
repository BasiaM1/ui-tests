package com.basia;

import com.basia.api.dto.register.RegisterDto;
import com.basia.api.enums.InputFields;
import com.basia.helpers.ConstValues;
import com.basia.pages.HomePage;
import com.basia.pages.LoginPage;
import com.basia.pages.register.RegisterPage;
import com.basia.pages.register.RegisterPageValidator;
import com.basia.providers.UserProvider;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    RegisterPage registerPage = loginPage.goToRegisterPage();
    RegisterPageValidator validator = new RegisterPageValidator(registerPage);
    @Test
    public void shouldBeAbleToRegisterNewUserAndLogin() {
        RegisterDto newUser = UserProvider.getRandomUser();
        registerNewUser(newUser)
                .verifyAlertMessageContains(ConstValues.REGISTER_MESSAGE);

        loginPage
                .attemptLogin(newUser.getUsername(), newUser.getPassword(), HomePage.class)
                .verifyHeaderContains(newUser.getFirstName());
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
        registerPage.attemptRegister(UserProvider.getUserWithTooShortData(), RegisterPage.class);

        InputFields.getRegisterInputLabels().forEach(validator::assertErrorMessageForFieldIsDisplayed);

        InputFields.getRegisterInputLabels().forEach(validator::assertFieldHasRedBorder);
    }

    private LoginPage registerNewUser(RegisterDto user) {
        return loginPage
                .goToRegisterPage()
                .attemptRegister(user);
    }
}
