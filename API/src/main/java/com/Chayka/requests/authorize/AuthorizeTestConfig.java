package com.Chayka.requests.authorize;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope("singleton")
@ConfigurationProperties(prefix = "authorize")
public class AuthorizeTestConfig {
    private String basePath;
    private Integer expectedPositiveResponseCode;
    private String expectedTokenType;
    private Integer expectedTokenExpirationTime;
    private String defaultGrantType;
    private String defaultUsername;
    private String defaultPassword;
}
