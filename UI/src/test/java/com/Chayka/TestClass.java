package com.Chayka;

import com.Chayka.pageObjects.AdminLoginPage;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestClass {
    @Autowired
    protected TestConfig testConfig;
    @Autowired
    protected AdminLoginPage adminLoginPage;

    @BeforeAll
    public void beforeClass(){
        Configuration.browser = "chrome";
        Configuration.baseUrl = testConfig.getBaseUrl();
        Configuration.browserSize = String.format("%dx%d",
                testConfig.getDefaultWindowSize().width,
                testConfig.getDefaultWindowSize().height);
        open("");
    }

    @Test
    public void test(){
        System.out.println("f");
    }
}
