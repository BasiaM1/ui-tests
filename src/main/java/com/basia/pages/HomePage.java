package com.basia.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HomePage extends AbstractPage {

    @FindBy(css = "h1")
    private WebElement header;

    @FindBy(css = "ul li")
    private List<WebElement> users;

    private WebElement editButton(String firstName, String lastName) {
        return driver.findElement(By.xpath(String.format("//ul//li[text()='%s']/span/a[contains(@class, 'edit')]",
                String.format("%s %s",firstName, lastName))));
    }

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage verifyHeaderContains(String firstName) {
        wait.until(ExpectedConditions.textToBePresentInElement(header, firstName));
        return this;
    }

    public void verifyUserCount(int userCount) {
        assertThat(users).hasSize(userCount);
    }

    public EditPage goToEditUserDetails(String firstName, String lastName){
        editButton(firstName,lastName).click();
        return new EditPage(getDriver());
    }
}
