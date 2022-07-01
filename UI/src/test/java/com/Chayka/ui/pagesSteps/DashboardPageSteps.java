package com.Chayka.ui.pagesSteps;

import com.Chayka.ui.pageObjects.DashboardPage;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class DashboardPageSteps extends UiTests {
    @Autowired
    protected DashboardPage dashboardPage;

    @Then("admin panel is loaded")
    public void adminPanelIsLoaded() {
        dashboardPage.checkIfAdminPanelIsShown();
    }

    @Then("user is redirected to Dashboard page")
    public void userIsRedirectedToDashboardPage() {
        dashboardPage.checkIfItsDashboardPage();
    }
}
