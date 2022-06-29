package com.Chayka;

import lombok.Getter;
import org.openqa.selenium.Dimension;
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
    private final String adminLoginPath;
    private final long defaultTimeoutLength;
    private final long defaultDelayLength;
    private final Dimension defaultWindowSize;

    @Getter
    private static TestConfig uniqueInstance;

    private TestConfig(@Value("${baseUrl}") String baseUrl,
                       @Value("${adminLoginPath}") String adminLoginPath,
                       @Value("${defaultTimeoutLength}") long defaultTimeoutLength,
                       @Value("${defaultDelayLength}") long defaultDelayLength,
                       @Value("${defaultWindowXSize}") int defaultWindowXSize,
                       @Value("${defaultWindowYSize}") int defaultWindowYSize) {
        this.baseUrl = baseUrl;
        this.adminLoginPath = adminLoginPath;
        this.defaultTimeoutLength = defaultTimeoutLength;
        this.defaultDelayLength = defaultDelayLength;
        this.defaultWindowSize = new Dimension(defaultWindowXSize, defaultWindowYSize);
        uniqueInstance = this;
    }
}
