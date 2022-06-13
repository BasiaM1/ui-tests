package com.basia.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends AbstractPage {

    @FindBy(css = "h1")
    private WebElement header;

    protected HomePage(WebDriver driver) {
        super(driver);
    }

    public void verifyHeaderContains(String firstName) {
        wait.until(ExpectedConditions.textToBePresentInElement(header, firstName));
    }
}
