package com.Chayka.ui.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;

import static com.Chayka.ui.uiElements.PlayersPageUIECN.*;

/**
 * Page Object that represents Players page of the application
 */
public class PlayersPage extends MainPageObject {
    private final SelenideElement playerManagementHeading;
    private final SelenideElement playersTable;
    private final ElementsCollection columnsButtons;
    private final ElementsCollection tableRows;

    public PlayersPage() {
        playerManagementHeading = $$(byClassName(PLAYER_MANAGEMENT_HEADING.getClassName()))
                .find(exactText(" Player management\t\t"));
        playersTable = $(byClassName("table"));
        tableRows = playersTable.$(byTagName("tbody")).$$(byTagName("tr"));
        columnsButtons = $$(byClassName(COLUMN_BUTTON.getClassName()));
    }

    /**
     * Check if currently user really is on the Players page
     * @return Players PO
     */
    public PlayersPage checkIfItsPlayersPage() {
        //Crutch to make Selenide to wait until Players page won't be loaded
        playerManagementHeading.should(exist);
        Assertions.assertThat(WebDriverRunner.getWebDriver().getCurrentUrl())
                .isEqualTo(testConfig.getBaseUrl() + testConfig.getPlayersPath());
        return this;
    }

    public PlayersPage checkIfPlayersTableExist() {
        playersTable.should(exist);
        return this;
    }

    /**
     * Searches for players table column name button by its name and clicks on it
     * <br>Fails the test if button wasn't found
     * @return Players PO
     */
    public PlayersPage clickOnColumnName(String columnName) {
        SelenideElement columnButton = columnsButtons.find(exactText(columnName));
        columnButton.should(exist);
        columnButton.click();
        return this;
    }

    /**
     * Checks if table is sorted correctly by some column (currently checks only ascending order)
     * @return Players PO
     */
    public PlayersPage checkTableSortByColumn(String columnName) {
        //Delay to give sorting some time to work
        launchDelay(2000);
        int fieldIndex = switch (columnName) {
            case "Username" -> 1;
            case "External ID" -> 2;
            case "Name" -> 3;
            case "Last name" -> 4;
            default -> 0;
        };

        List<String> usernamesList = new ArrayList<>();
        //No, you can't use enhanced 'for' with ElementsCollection
        for (int i = 0; i < tableRows.size(); i++) {
            usernamesList.add(tableRows.get(i).$$(byTagName("td")).get(fieldIndex).getText());
        }
        for (int i = 1; i < usernamesList.size(); i++) {
            Assertions.assertThat(usernamesList.get(i - 1)).isLessThanOrEqualTo(usernamesList.get(i));
        }
        return this;
    }
}
