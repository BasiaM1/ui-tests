package com.basia;

import com.basia.api.ApiLogin;
import com.basia.api.ApiRegister;
import com.basia.api.dto.login.LoginDto;
import com.basia.api.dto.login.LoginResponseDto;
import com.basia.api.dto.register.RegisterDto;
import com.basia.config.YamlParser;
import com.basia.pages.HomePage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.basia.providers.UserProvider.getRandomUser;

public class HomePageTest extends BaseTest {

    private final ApiRegister apiRegister = new ApiRegister();
    private final ApiLogin apiLogin = new ApiLogin();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RegisterDto user = getRandomUser();

    @SneakyThrows
    @BeforeMethod
    public void login() {
        apiRegister.register(user);
        LoginResponseDto loginResponse = apiLogin.login(new LoginDto(user.getUsername(), user.getPassword()));
        String userValue = objectMapper.writeValueAsString(loginResponse);
        LocalStorage local = ((WebStorage) driver).getLocalStorage();
        local.setItem("user", userValue);
    }

    @Test
    void shouldBeOnHomePage() {
        driver.navigate().to(YamlParser.getConfig().getUrl());
        HomePage homePage = new HomePage(driver);
        homePage.verifyHeaderContains(user.getFirstName());
    }
}
