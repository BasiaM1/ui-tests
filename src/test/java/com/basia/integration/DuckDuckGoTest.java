package com.basia.integration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class DuckDuckGoTest {

    WebDriver driver;

    @Test
    public void testDuckDuckGoInSearchResults() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("https://www.google.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("duckduckgo");
        searchBox.submit();

        List<WebElement> searchResults = driver.findElements(By.xpath("//div[@class='tF2Cxc']//h3"));

        boolean foundDuckDuckGo = false;
        for (WebElement result : searchResults) {
            if (result.getText().toLowerCase().contains("duckduckgo")) {
                foundDuckDuckGo = true;
                break;
            }
        }

        Assert.assertTrue(foundDuckDuckGo, "DuckDuckGo is not found in search results");
    }
}