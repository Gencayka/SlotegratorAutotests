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

public class PlayersPage extends MainPageObject {
    private final SelenideElement playerManagementHeading;
    private final SelenideElement playersTable;
    private final ElementsCollection columnsButtons;
    private final ElementsCollection tableRows;

    public PlayersPage() {
        playerManagementHeading = $$(byClassName("panel-heading")).find(exactText(" Player management\t\t"));
        playersTable = $(byClassName("table"));
        tableRows = playersTable.$(byTagName("tbody")).$$(byTagName("tr"));
        columnsButtons = $$(byClassName("sort-link"));
    }

    public PlayersPage checkIfItsPlayersPage() {
        playerManagementHeading.should(exist);
        Assertions.assertThat(WebDriverRunner.getWebDriver().getCurrentUrl())
                .isEqualTo(testConfig.getBaseUrl() + testConfig.getPlayersPath());
        return this;
    }

    public PlayersPage checkIfPlayersTableExist() {
        playersTable.should(exist);
        return this;
    }

    public PlayersPage clickOnColumnName(String columnName) {
        SelenideElement columnButton = columnsButtons.find(exactText(columnName));
        columnButton.should(exist);
        columnButton.click();
        return this;
    }

    public PlayersPage checkTableSortByStringField(String fieldName) {
        launchDelay(2000);
        int fieldIndex = switch (fieldName) {
            case "Username" -> 1;
            case "External ID" -> 2;
            case "Name" -> 3;
            case "Last name" -> 4;
            default -> 0;
        };

        List<String> usernamesList = new ArrayList<>();
        for (int i = 0; i < tableRows.size(); i++) {
            usernamesList.add(tableRows.get(i).$$(byTagName("td")).get(fieldIndex).getText());
        }
        for (int i = 1; i < usernamesList.size(); i++) {
            Assertions.assertThat(usernamesList.get(i - 1)).isLessThanOrEqualTo(usernamesList.get(i));
        }
        return this;
    }
}
