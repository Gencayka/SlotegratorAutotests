package com.Chayka.ui.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

import static com.Chayka.ui.uiElements.MainPageUIECN.*;

public abstract class MainPageObject extends SlotegratorPageObject {
    @Autowired
    @Getter
    protected DashboardPage dashboardPage;
    @Autowired
    @Getter
    protected PlayersPage playersPage;

    protected final SelenideElement profileButton;
    protected final SelenideElement activeSideButton;
    protected final ElementsCollection sideButtons;
    private final ElementsCollection sideSubButtons;

    public MainPageObject(){
        profileButton = $(byClassName(PROFILE_BUTTON.getClassName()));
        sideButtons = $(byClassName(SIDE_BUTTONS.getClassName())).$$(byTagName("li"));
        activeSideButton = sideButtons.find(cssClass("active"));
        sideSubButtons = activeSideButton.$$(byTagName("li"));
    }

    public void clickSideButton(String buttonName){
        SelenideElement sideButton = sideButtons.find(exactText(buttonName));
        sideButton.should(exist);
        if(sideButton!=activeSideButton){
            sideButton.click();
        }
    }

    public void clickSideSubButton(String buttonName){
        SelenideElement sideSubButton = sideSubButtons.find(exactText(buttonName));
        sideSubButton.should(exist);
        if(sideSubButton!=activeSideButton){
            sideSubButton.click();
        }
    }

    public DashboardPage goToDashboardPage(){
        clickSideButton("Dashboard");
        return dashboardPage.checkIfItsDashboardPage();
    }

    public PlayersPage goToPlayersPage(){
        clickSideButton("Users");
        clickSideSubButton("Players");
        return playersPage.checkIfItsPlayersPage();
    }

    public void goToPage(String pageName){
        switch (pageName) {
            case "Dashboard" -> goToDashboardPage();
            case "Players" -> goToPlayersPage();
        }
    }

    public void checkIfAdminPanelIsShown(){
        profileButton.should(exist);
        profileButton.shouldHave(exactText(testConfig.getAdminLogin()));
    }
}
