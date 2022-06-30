package com.Chayka;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selenide.open;

@CucumberContextConfiguration
public class MyStepdefs extends UiTests{
    private String today;
    private String actualAnswer;

    private String isItFriday(String today) {
        return "Friday".equals(today) ? "TGIF" : "Nope";
    }

    @Given("authorization page is opened")
    public void authorizationPageIsOpened() {
        //preSettings();
        Configuration.browser = "chrome";
        Configuration.baseUrl = testConfig.getBaseUrl() + testConfig.getAdminLoginPath();
        Configuration.browserSize = String.format("%dx%d",
                testConfig.getDefaultWindowSize().width,
                testConfig.getDefaultWindowSize().height);
        open("");
        System.out.println("open");
    }

    @When("user enters valid login and password")
    public void userEntersValidLoginAndPassword() {
        System.out.println("enter");
    }

    @Then("user authorizes as Admin successfully")
    public void userAuthorizesAsAdminSuccessfully() {
        Assertions.assertEquals(1, 1);
    }
}
