package com.Chayka.ui.pageObjects;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;

import static com.Chayka.ui.uiElements.AdminLoginPageUIECN.*;

/**
 * Page Object that represents Admin Login page of the application
 */
public class AdminLoginPage extends SlotegratorPageObject{
    private final SelenideElement loginInputForm;
    private final SelenideElement passwordInputForm;
    private final SelenideElement signInButton;

    @Autowired
    @Getter
    private DashboardPage dashboardPage;

    public AdminLoginPage(){
        ElementsCollection inputForms = $$(byClassName(INPUT_FORM.getClassName()));
        loginInputForm = inputForms.find(attribute("placeholder", "Login"));
        passwordInputForm = inputForms.find(attribute("placeholder", "Password"));

        signInButton = $(byClassName("btn-primary"));
    }

    public AdminLoginPage openAdminLoginPage(){
        Configuration.baseUrl = testConfig.getBaseUrl() + testConfig.getAdminLoginPath();
        open("");
        return this;
    }

    public AdminLoginPage enterLogin(String login){
        loginInputForm.setValue(login);
        return this;
    }

    public AdminLoginPage enterPassword(String password){
        passwordInputForm.setValue(password);
        return this;
    }

    public AdminLoginPage clickSignInButton(){
        signInButton.click();
        return this;
    }

    /**
     * Authorizes as Admin successfully and checks that authorizes was really successful
     * @return Dashboard PO
     */
    public DashboardPage authorizeAsAdmin(){
        this
                .openAdminLoginPage()
                .enterLogin(testConfig.getAdminLogin())
                .enterPassword(testConfig.getAdminPassword())
                .clickSignInButton();
        return dashboardPage.checkIfItsDashboardPage();
    }
}
