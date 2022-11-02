package com.basia.integration;

import org.springframework.test.annotation.DirtiesContext;
import org.testng.annotations.Test;

@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class SmokeTest extends BaseTest {

    @Test
    void test() {
        localDriverManager.getDriver().navigate().to(config.getUrl());
    }

    @Test
    void test2() {
        localDriverManager.getDriver().navigate().to(config.getUrl());
    }
}
