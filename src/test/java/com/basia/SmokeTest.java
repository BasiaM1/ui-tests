package com.basia;

import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test
    void test() {
        driver.navigate().to("http://localhost:8081/login");
    }
}
