package pages;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends AbstractPage {

    @FindBy(name = "username")
    private WebElement usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(className = "btn-primary")
    private WebElement loginButton;

    @FindBy(className = "btn-link")
    private WebElement registerButton;

    @FindBy(className = "alert")
    private WebElement alert;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @SneakyThrows
    public <T extends AbstractPage> T attemptLogin(String username, String password, Class<T> expectedPage) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
        return expectedPage.getDeclaredConstructor(WebDriver.class).newInstance(driver);
    }

    public LoginPage attemptLogin(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
        return this;
    }

    public void verifyAlertMessageContains(String text) {
        wait.until(ExpectedConditions.textToBePresentInElement(alert, text));
    }
}
