package com.basia.pages;

import com.basia.config.LocalDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public abstract class AbstractPage {

    @Autowired
    protected final LocalDriverManager localDriverManager;

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected AbstractPage(LocalDriverManager localDriverManager) {
        this.localDriverManager = localDriverManager;
        this.driver = localDriverManager.getDriver();
        this.wait = new WebDriverWait(localDriverManager.getDriver(), Duration.of(5, ChronoUnit.SECONDS));
        PageFactory.initElements(localDriverManager.getDriver(), this);
    }

    public WebDriver getDriver() {
        return localDriverManager.getDriver();
    }
}
