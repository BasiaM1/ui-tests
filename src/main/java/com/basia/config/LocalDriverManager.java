package com.basia.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.remote.Augmenter;
import org.springframework.stereotype.Component;

@Component
public class LocalDriverManager {

    private WebDriver driver;

    public WebDriver getDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", "/Users/ocado/Downloads/chromedriver");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-web-security");
            driver = new ChromeDriver(chromeOptions);
            driver = new Augmenter().augment(driver);
            DevTools devTools = ((HasDevTools) driver).getDevTools();
            devTools.createSession();
        }
        return driver;
    }

    public void invalidateDriver() {
        driver = null;
    }

}
