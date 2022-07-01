package com.Chayka.ui.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public abstract class MainPageObject extends SlotegratorPageObject {
    @Autowired
    @Getter
    protected DashboardPage dashboardPage;
    @Autowired
    @Getter
    protected PlayersPage playersPage;

    protected final SelenideElement profileButton;

    protected final SelenideElement activeSideButton;

    //protected final SelenideElement dashboardButton;

    //protected final SelenideElement usersButton;
    //protected final SelenideElement playersButton;
    //protected final SelenideElement playersRegistrationsButton;

    protected final ElementsCollection sideButtons;
    private final ElementsCollection sideSubButtons;

    public MainPageObject(){
        profileButton = $(byClassName("nav-profile"));

        /*ElementsCollection*/ sideButtons = $(byClassName("main-side-menu")).$$(byTagName("li"));
        activeSideButton = sideButtons.find(cssClass("active"));

        //dashboardButton = sideButtons.find(exactText("Dashboard"));

        //usersButton = sideButtons.find(exactText("Users"));
        //ElementsCollection usersSubButtons = activeSideButton.$$(byTagName("li"));
        //playersButton = usersSubButtons.find(text("Players"));
        //playersRegistrationsButton = usersSubButtons.find(text("Players registrations"));
        sideSubButtons = activeSideButton.$$(byTagName("li"));
    }

    /*public DashboardPage goToDashboardPage(){
        if(dashboardButton!=activeSideButton){
            dashboardButton.click();
        }
        return dashboardPage.checkIfItsDashboardPage();
    }

    public PlayersPage goToPlayersPage(){
        if(usersButton!=activeSideButton){
            usersButton.click();
        }
        if(playersButton!=activeSideButton){
            playersButton.click();
        }
        return playersPage;
    }*/

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
