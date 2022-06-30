package com.Chayka;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.Dimension;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope("singleton")
@ConfigurationProperties(prefix = "commons")
public class TestConfig {
    private String baseUrl;
    private String adminLoginPath;
    private long defaultTimeoutLength;
    private long defaultDelayLength;
    private Dimension defaultWindowSize;

    @Getter(AccessLevel.NONE)
    private int defaultWindowXSize;
    @Getter(AccessLevel.NONE)
    private int defaultWindowYSize;

    public void setDefaultWindowXSize(int defaultWindowXSize) {
        this.defaultWindowXSize = defaultWindowXSize;
        this.defaultWindowSize = new Dimension(defaultWindowXSize, defaultWindowYSize);
    }

    public void setDefaultWindowYSize(int defaultWindowYSize) {
        this.defaultWindowYSize = defaultWindowYSize;
        this.defaultWindowSize = new Dimension(defaultWindowXSize, defaultWindowYSize);
    }
}
