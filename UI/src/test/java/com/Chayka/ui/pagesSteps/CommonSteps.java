package com.Chayka.ui.pagesSteps;

import com.Chayka.ui.pageObjects.AdminLoginPage;
import com.Chayka.ui.pageObjects.DashboardPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonSteps extends UiTests{
    @Autowired
    protected AdminLoginPage adminLoginPage;
    @Autowired
    protected DashboardPage mainPage;

    @Given("{string} page is opened")
    public void pageIsOpened(String pageName) {
        adminLoginPage
                .authorizeAsAdmin();
        mainPage.goToPage(pageName);
    }

    @When("user clicks on {string} side button")
    public void userClicksOnSideButton(String buttonName) {
        mainPage.clickSideButton(buttonName);
    }

    @And("user clicks on {string} side subbutton")
    public void userClicksOnSideSubbutton(String buttonName) {
        mainPage.clickSideSubButton(buttonName);
    }
}
