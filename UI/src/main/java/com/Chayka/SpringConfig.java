package com.Chayka;

import com.Chayka.pageObjects.AdminLoginPage;
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
}
