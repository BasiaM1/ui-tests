package com.basia.isolation;

import com.basia.api.dto.register.RegisterDto;
import com.basia.api.dto.register.RegisterResponseDto;
import com.basia.enums.InputFields;
import com.basia.helpers.ConstValues;
import com.basia.pages.LoginPage;
import com.basia.pages.register.RegisterPage;
import com.basia.pages.register.RegisterPageValidator;
import com.basia.providers.UserProvider;
import lombok.SneakyThrows;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.testng.annotations.Test;

import static org.openqa.selenium.remote.http.Contents.utf8String;

public class IsolatedRegisterTest extends IsolatedBaseTest {

    RegisterPage registerPage;
    RegisterPageValidator validator;
    private final RegisterDto newUser = UserProvider.getRandomUser();
    private final RegisterDto incorrectRegisterUserData = UserProvider.getUserWithTooShortData();

    @Test
    public void shouldBeAbleToRegisterNewUser() {
        mockSuccessfulRegister();
        registerNewUser(newUser)
                .verifyAlertMessageContains(ConstValues.REGISTER_MESSAGE);
    }

    @Test
    public void shouldFailRegisterUserWithIncorrectDate() {
        mockUnsuccessfulRegister();

        registerPage = loginPage.goToRegisterPage()
                .attemptRegister(incorrectRegisterUserData, RegisterPage.class);

        validator = new RegisterPageValidator(registerPage);

        InputFields.getRegisterInputLabels().forEach(validator::assertErrorMessageForFieldIsDisplayed);
        InputFields.getRegisterInputLabels().forEach(validator::assertFieldHasRedBorder);
    }


    private void mockSuccessfulRegister() {
        new NetworkInterceptor(
                driver,
                Route.matching(req -> req.getUri().contains("/users/signup"))
                        .to(() -> req ->
                                new HttpResponse()
                                        .setStatus(200)
                                        .setContent(utf8String(getToken()))));
    }

    @SneakyThrows
    private String getToken() {
        RegisterResponseDto response = RegisterResponseDto.builder()
                .token("fakeToken")
                .build();

        return objectMapper.writeValueAsString(response);
    }

    private void mockUnsuccessfulRegister() {//user already exists
        new NetworkInterceptor(
                driver,
                Route.matching(req -> req.getUri().contains("/users/signup"))
                        .to(() -> req ->
                                new HttpResponse()
                                        .setStatus(400)
                                        .setContent(utf8String(buildFailedRegisterResponseBody())))
        );
    }

    private LoginPage registerNewUser(RegisterDto user) {
        return loginPage
                .goToRegisterPage()
                .attemptRegister(user);
    }

    @SneakyThrows
    private String buildFailedRegisterResponseBody() { //objectMapper
        return "{\"firstName\":\"Required firstName length is 4 or more\"," +
                "\"lastName\":\"Required lastName length is 4 or more\"," +
                "\"password\":\"Required password length is 4 or more\"," +
                "\"email\": " + "\"" + ConstValues.EMAIL_ERROR_MESSAGE + "\"," +
                "\"username\":\"Required username length is 4 or more\"}";
    }
}
