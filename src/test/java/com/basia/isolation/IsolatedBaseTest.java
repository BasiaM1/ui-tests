package com.basia.isolation;

import com.basia.api.dto.userdetails.UserDetailsDto;
import com.basia.config.LocalConfig;
import com.basia.config.SpringConfig;
import com.basia.pages.LoginPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
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

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.apache.commons.io.IOUtils.resourceToString;

@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = SpringConfig.class)
@EnableConfigurationProperties
public abstract class IsolatedBaseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected LocalConfig config;

    public static final String USERS_AS_STRING = loadFile("/users.json");

    @Autowired
    protected WebDriver driver;
    @Autowired
    protected LoginPage loginPage;

    @Autowired
    protected ObjectMapper objectMapper;

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
        driver.navigate().to(config.getUrl());

        return loginPage;
    }

    @SneakyThrows
    protected List<UserDetailsDto> getUsersList() {
        return objectMapper.readValue(USERS_AS_STRING, new TypeReference<>() {});
    }
}
