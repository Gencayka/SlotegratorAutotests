package com.Chayka;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("singleton")
@PropertySource("classpath:testConfig.properties")
public class TestConfig {
    private final String baseUrl;
    private final String getTokenPath;
    private final String registerPlayerPath;
    private final String getPlayersProfilePath;
    private final String basicAuthenticationUsername;

    @Getter
    private static TestConfig uniqueInstance;

    private TestConfig(@Value("${baseUrl}") String baseUrl,
                       @Value("${getTokenPath}") String getTokenPath,
                       @Value("${registerPlayerPath}") String registerPlayerPath,
                       @Value("${getPlayersProfilePath}") String getPlayersProfilePath,
                       @Value("${basicAuthenticationUsername}") String basicAuthenticationUsername) {
        this.baseUrl = baseUrl;
        this.getTokenPath = getTokenPath;
        this.registerPlayerPath = registerPlayerPath;
        this.getPlayersProfilePath = getPlayersProfilePath;
        this.basicAuthenticationUsername = basicAuthenticationUsername;
        uniqueInstance = this;
    }
}
