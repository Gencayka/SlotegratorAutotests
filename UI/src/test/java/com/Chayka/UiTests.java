package com.Chayka;

import com.Chayka.pageObjects.AdminLoginPage;
import com.codeborne.selenide.Configuration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.codeborne.selenide.Selenide.open;

@SpringBootTest
public abstract class UiTests {
    @Autowired
    protected TestConfig testConfig;
    @Autowired
    protected AdminLoginPage adminLoginPage;

    protected void preSettings(){
        Configuration.browser = "chrome";
        Configuration.baseUrl = testConfig.getBaseUrl() + testConfig.getAdminLoginPath();
        Configuration.browserSize = String.format("%dx%d",
                testConfig.getDefaultWindowSize().width,
                testConfig.getDefaultWindowSize().height);
    }
}
