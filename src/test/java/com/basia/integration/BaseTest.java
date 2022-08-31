package com.basia.integration;

import com.basia.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.remote.Augmenter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


import static com.basia.config.YamlParser.getConfig;

public abstract class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;

    @BeforeClass
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    protected void setupTest() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-web-security");
        driver = new ChromeDriver(chromeOptions);
        driver = new Augmenter().augment(driver);
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
        goToPage();
    }

    @AfterMethod
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void goToPage() {
        driver.navigate().to(getConfig().getUrl());
        loginPage = new LoginPage(driver);

    }

}
