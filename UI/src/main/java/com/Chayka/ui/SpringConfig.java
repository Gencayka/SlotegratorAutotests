package com.Chayka.ui;

import com.Chayka.ui.pageObjects.AdminLoginPage;
import com.Chayka.ui.pageObjects.DashboardPage;
import com.Chayka.ui.pageObjects.PlayersPage;
import org.assertj.core.api.SoftAssertions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public SoftAssertions getSoftAssertions(){
        return new SoftAssertions();
    }
    @Bean
    public AdminLoginPage adminLoginPage(){
        return new AdminLoginPage();
    }
    @Bean
    public DashboardPage dashboardPage(){return new DashboardPage();}
    @Bean
    public PlayersPage playersPage(){return new PlayersPage();}
}
