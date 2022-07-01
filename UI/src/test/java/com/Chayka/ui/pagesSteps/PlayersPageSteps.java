package com.Chayka.ui.pagesSteps;

import com.Chayka.ui.pageObjects.PlayersPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class PlayersPageSteps extends UiTests{
    @Autowired
    private PlayersPage playersPage;

    @Then("user is redirected to Players page")
    public void userIsRedirectedToPlayersPage() {
        playersPage.checkIfItsPlayersPage();
    }

    @And("players table is loaded")
    public void playersTableIsLoaded() {
        playersPage.checkIfPlayersTableExist();
    }

    @When("user clicks on {string} column name in Players table")
    public void userClicksOnColumnNameInPlayersTable(String columnName) {
        playersPage.clickOnColumnName(columnName);
    }

    @Then("players table is sorted by {string}")
    public void playersTableIsSortedBy(String columnName) {
        playersPage.checkTableSortByColumn(columnName);
    }
}
