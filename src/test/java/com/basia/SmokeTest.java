package com.basia;

import com.basia.config.YamlParser;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test
    void test() {
        driver.navigate().to(YamlParser.getConfig().getUrl());
    }
}
