package com.Chayka.ui.pagesSteps;

import com.Chayka.ui.TestConfig;
import com.Chayka.ui.pageObjects.AdminLoginPage;
import com.Chayka.ui.pageObjects.DashboardPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.codeborne.selenide.Selenide.open;

@SpringBootTest
public abstract class UiTests {
    @Autowired
    protected TestConfig testConfig;
}
