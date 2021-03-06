package com.basia;

import com.basia.api.dto.RegisterDto;
import com.basia.api.enums.RegisterFields;
import com.basia.helpers.ConstValues;
import com.basia.pages.HomePage;
import com.basia.pages.LoginPage;
import com.basia.pages.RegisterPage;
import com.basia.pages.RegisterPageValidator;
import com.basia.providers.UserProvider;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

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
        RegisterPage registerPage = loginPage.goToRegisterPage();
        RegisterPageValidator validator = new RegisterPageValidator(registerPage);

        registerPage.attemptRegister(UserProvider.getUserWithTooShortData(), RegisterPage.class);

        RegisterFields.getRegisterFieldsLabels().forEach(validator::assertErrorMessageForFieldIsDisplayed);

        RegisterFields.getRegisterFieldsLabels().forEach(validator::assertFieldHasRedBorder);
    }


    private LoginPage registerNewUser(RegisterDto user) {
        return loginPage
                .goToRegisterPage()
                .attemptRegister(user);

    }
}
