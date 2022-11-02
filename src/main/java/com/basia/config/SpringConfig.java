package com.basia.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.remote.Augmenter;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(value = {"com.basia"})
@PropertySource(value={"classpath:application.yml"})
public class SpringConfig {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Bean
    public ObjectMapper objectMapper() {
        return OBJECT_MAPPER;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public WebDriver webDriver() {
        System.setProperty("webdriver.chrome.driver","/Users/bmurzynowska/Downloads/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-web-security");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver = new Augmenter().augment(driver);
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        return driver;
    }

}
