package com.basia.integration;

import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test
    void test() {
        driver.navigate().to(config.getUrl());
    }
}
