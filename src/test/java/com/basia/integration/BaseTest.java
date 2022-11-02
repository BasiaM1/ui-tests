package com.basia.integration;

import com.basia.api.ApiDeleteUser;
import com.basia.api.ApiGetAllUsers;
import com.basia.api.ApiRegister;
import com.basia.api.ApiUserDetails;
import com.basia.config.LocalConfig;
import com.basia.config.SpringConfig;
import com.basia.config.LocalDriverManager;
import com.basia.pages.EditPage;
import com.basia.pages.EditPageValidator;
import com.basia.pages.HomePage;
import com.basia.pages.LoginPage;
import com.basia.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = SpringConfig.class)
@EnableConfigurationProperties
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected LocalDriverManager localDriverManager;

    @Autowired
    protected LocalConfig config;
    @Autowired
    protected ApiUserDetails apiUserDetails;
    @Autowired
    protected ApiDeleteUser apiDeleteUser;
    @Autowired
    protected LoginUtil loginUtil;
    @Autowired
    protected HomePage homePage;
    @Autowired
    protected EditPageValidator editPageValidator;
    @Autowired
    protected EditPage editPage;
    @Autowired
    protected ApiGetAllUsers apiGetAllUsers;
    @Autowired
    protected ApiRegister apiRegister;
    @Autowired
    protected LoginPage loginPage;

//    @BeforeClass
//    static void setupClass() {
//        WebDriverManager.chromedriver().setup();
//    }

    @BeforeMethod
    protected void setupTest() {
        goToPage();
    }

    @AfterMethod
    void teardown() {
        if (localDriverManager.getDriver() != null) {
            localDriverManager.getDriver().quit();
            localDriverManager.invalidateDriver();
        }
    }

    private void goToPage() {
        localDriverManager.getDriver().navigate().to(config.getUrl());
    }

}
