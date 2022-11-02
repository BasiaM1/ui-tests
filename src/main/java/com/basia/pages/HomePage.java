package com.basia.pages;

import lombok.Getter;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Component
@Getter
public class HomePage extends AbstractPage {

    @FindBy(css = "h1")
    private WebElement header;

    @FindBy(css = "ul li")
    private List<WebElement> users;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private WebElement editButton(String firstName, String lastName) {
        return driver.findElement(By.xpath(String.format("//ul//li[text()='%s']/span/a[contains(@class, 'edit')]",
                String.format("%s %s", firstName, lastName))));
    }
    private WebElement editButton1() {
        List<WebElement> edit = driver.findElements(By.className("edit"));
        return edit.get(edit.size()-1);
    }

    private WebElement deleteButton(String fullName) {
        return driver.findElement(By.xpath(String.format("//ul//li[text()='%s']/span/a[contains(@class, 'delete')]", fullName)));
    }

    public HomePage verifyHeaderContains(String firstName) {
        wait.until(ExpectedConditions.textToBePresentInElement(header, firstName));
        return this;
    }

    public void verifyUserCount(int userCount) {
        assertThat(users).hasSize(userCount);
    }

    public EditPage goToEditUserDetails(String firstName, String lastName) {
        editButton(firstName, lastName).click();
        return new EditPage(getDriver());
    }

    public EditPage goToEditUserDetails1() {
        editButton1().click();
        return new EditPage(getDriver());
    }

    public HomePage deleteAllUsersWithoutDefault(String loggedUserName) {
        users.stream()
                .filter(el -> filterOutUsers(loggedUserName, el))
                .forEach(this::deleteUser);

        return this;
    }

    private void deleteUser(WebElement el) {
        el.findElement(By.className("delete")).click();

        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    private boolean filterOutUsers(String loggedUserName, WebElement el) {
        return !el.getText().contains("Slawomir") && !el.getText().contains("Gosia") && !el.getText().contains(loggedUserName);
    }
}
