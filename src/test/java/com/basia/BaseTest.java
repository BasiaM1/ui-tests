package com.basia;

import com.basia.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import static com.basia.config.YamlParser.getConfig;

public abstract class BaseTest {

    protected WebDriver driver;
    public LoginPage loginPage;

    @BeforeClass
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    void setupTest() {
        driver = new ChromeDriver();
        goToLoginPage();
    }

    @AfterMethod
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private LoginPage goToLoginPage() {
        driver.navigate().to(getConfig().getUrl());
        loginPage = new LoginPage(driver);

        return loginPage;
    }
}
