package com.basia.isolation;

import com.basia.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.remote.Augmenter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.nio.charset.StandardCharsets;

import static com.basia.config.YamlParser.getConfig;
import static org.apache.commons.io.IOUtils.resourceToString;

public abstract class IsolatedBaseTest {
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

    @SneakyThrows
    protected static String loadFile(String file) {
        return resourceToString(file, StandardCharsets.UTF_8);
    }

    @AfterMethod
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private LoginPage goToPage() {
        driver.navigate().to(getConfig().getUrl());
        loginPage = new LoginPage(driver);

        return loginPage;
    }

}