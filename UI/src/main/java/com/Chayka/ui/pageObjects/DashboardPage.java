package com.Chayka.ui.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.assertj.core.api.Assertions;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;

public class DashboardPage extends MainPageObject{
    private final SelenideElement depositsPanel;

    public DashboardPage(){
        depositsPanel = $(byClassName("col-sm-12"));
    }
    public DashboardPage checkIfItsDashboardPage(){
        depositsPanel.should(exist);
        Assertions.assertThat(WebDriverRunner.getWebDriver().getCurrentUrl())
                .isEqualTo(testConfig.getBaseUrl() + testConfig.getDashboardPath());
        return this;
    }
}
