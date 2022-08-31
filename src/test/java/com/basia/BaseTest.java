package com.basia;

import com.basia.api.ApiDeleteUser;
import com.basia.api.ApiGetAllUsers;
import com.basia.api.ApiUserDetails;
import com.basia.pages.LoginPage;
import com.google.common.net.MediaType;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.nio.charset.StandardCharsets;

import static com.basia.config.YamlParser.getConfig;
import static org.apache.commons.io.IOUtils.resourceToString;
import static org.openqa.selenium.remote.http.Contents.utf8String;

public abstract class BaseTest {
    protected ApiUserDetails apiUserDetails = new ApiUserDetails();
    protected ApiDeleteUser apiDeleteUser = new ApiDeleteUser();
    protected ApiGetAllUsers apiGetAllUsers = new ApiGetAllUsers();
    protected WebDriver driver;
    protected LoginPage loginPage;

    @BeforeClass
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    void setupTest() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-web-security");
        driver = new ChromeDriver(chromeOptions);
        driver = new Augmenter().augment(driver);
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        new NetworkInterceptor(
                driver,
                Route.matching(req -> req.getUri().endsWith("/users"))
                        .to(() -> req ->
                                new HttpResponse()
                                        .setStatus(200)
                                        .addHeader("Content-Type", MediaType.JSON_UTF_8.toString())
                                        .setContent(utf8String(loadFile("/users.json"))))
        );

        goToPage();
    }

    @SneakyThrows
    private static String loadFile(String file) {
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
