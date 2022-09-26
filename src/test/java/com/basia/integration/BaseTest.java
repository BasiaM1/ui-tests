package com.basia.integration;

import com.basia.config.SpringConfig;
import com.basia.config.LocalConfig;
import com.basia.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.remote.Augmenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = SpringConfig.class)
@EnableConfigurationProperties
public abstract class BaseTest extends AbstractTestNGSpringContextTests {
    protected WebDriver driver;
    protected LoginPage loginPage;

    @Autowired
    protected LocalConfig config;

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
        driver.navigate().to(config.getUrl());
        loginPage = new LoginPage(driver);

    }

}
