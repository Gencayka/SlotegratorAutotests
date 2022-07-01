package com.Chayka.ui.pagesSteps;

import com.Chayka.ui.pageObjects.AdminLoginPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import static com.codeborne.selenide.Selenide.open;

@CucumberContextConfiguration
public class AdminLoginPageSteps extends UiTests{
    @Autowired
    protected AdminLoginPage adminLoginPage;

    @Before
    public void before(){
        Configuration.browser = testConfig.getDefaultBrowser();
        Configuration.browserSize = String.format("%dx%d",
                testConfig.getDefaultWindowSize().width,
                testConfig.getDefaultWindowSize().height);
    }

    @After
    public void after(){
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.refresh();
    }

    @Given("authorization page is opened")
    public void authorizationPageIsOpened() {
        adminLoginPage.openAdminLoginPage();
    }

    @When("user enters valid login")
    public void userEntersValidLogin() {
        adminLoginPage.enterLogin(testConfig.getAdminLogin());
    }

    @And("user enters valid password")
    public void userEntersValidPassword() {
        adminLoginPage.enterPassword(testConfig.getAdminPassword());
    }

    @And("user clicks the sign in button")
    public void userClicksTheSignInButton() {
        adminLoginPage.clickSignInButton();
    }
}
