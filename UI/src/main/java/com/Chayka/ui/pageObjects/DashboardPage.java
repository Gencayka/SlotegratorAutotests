package com.Chayka.ui.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.assertj.core.api.Assertions;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;

import static com.Chayka.ui.uiElements.DashboardPageUIECN.*;

/**
 * Page Object that represents Dashboard page of the application
 */
public class DashboardPage extends MainPageObject{
    private final SelenideElement depositsPanel;

    public DashboardPage(){
        depositsPanel = $(byClassName(DEPOSIT_PANEL.getClassName()));
    }

    /**
     * Check if currently user really is on the Dashboard page
     * @return Dashboard PO
     */
    public DashboardPage checkIfItsDashboardPage(){
        //Crutch to make Selenide to wait until Dashboard page won't be loaded
        depositsPanel.should(exist);
        Assertions.assertThat(WebDriverRunner.getWebDriver().getCurrentUrl())
                .isEqualTo(testConfig.getBaseUrl() + testConfig.getDashboardPath());
        return this;
    }
}
