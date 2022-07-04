package com.basia.pages;

import com.basia.api.dto.RegisterDto;
import lombok.Getter;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Getter
public class RegisterPage extends AbstractPage {

    @FindBy(name = "firstName")
    private WebElement firstNameField;

    @FindBy(name = "lastName")
    private WebElement lastNameField;

    @FindBy(name = "username")
    private WebElement userNameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(css = "button[class*='btn-primary']")
    private WebElement registerButton;

    @FindBy(css = "a[href='/login'")
    private WebElement cancelButton;

    @FindBy(className = "alert")
    private WebElement alert;


    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getField(String label){
        return driver.findElement(By.xpath(String.format("//label[text()='%s']/following-sibling::input",label)));
    }

    public WebElement getErrorMessageForField(String label){
        By errorMessageXpath = By.xpath(String.format("//label[text()='%s']/following-sibling::div[@class='invalid-feedback']", label));
        wait.until(ExpectedConditions.presenceOfElementLocated(errorMessageXpath));

        return driver.findElement(errorMessageXpath);
    }

    @SneakyThrows
    public <T extends AbstractPage> T attemptRegister(RegisterDto user, Class<T> expectedPage) {
        firstNameField.sendKeys(user.getFirstName());
        lastNameField.sendKeys(user.getLastName());
        userNameField.sendKeys(user.getUsername());
        passwordField.sendKeys(user.getPassword());
        emailField.sendKeys(user.getEmail());
        registerButton.click();
        return expectedPage.getDeclaredConstructor(WebDriver.class).newInstance(driver);
    }

    public LoginPage attemptRegister(RegisterDto user) {
        firstNameField.sendKeys(user.getFirstName());
        lastNameField.sendKeys(user.getLastName());
        userNameField.sendKeys(user.getUsername());
        passwordField.sendKeys(user.getPassword());
        emailField.sendKeys(user.getEmail());
        registerButton.click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("h2"), "Login"));
        return new LoginPage(getDriver());
    }

    public void verifyAlertMessageContains(String text) {
        wait.until(ExpectedConditions.textToBePresentInElement(alert, text));
    }
}
