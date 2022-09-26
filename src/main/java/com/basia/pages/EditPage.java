package com.basia.pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EditPage extends AbstractPage {

    @FindBy(css = "h2")
    private WebElement title;

    @FindBy(css = "button[class*='btn-primary']")
    private WebElement editUserButton;

    @FindBy(css = "a[href='/'")
    private WebElement cancelButton;

    protected WebElement inputField(String label) {
        return driver.findElement(By.xpath(String.format("//label[text()='%s']/following-sibling::input", label)));
    }

    public EditPage(WebDriver driver) {
        super(driver);
    }

    public void editUserDetails(String label, String value){
        inputField(label).clear();
        inputField(label).sendKeys(value);
    }
}
