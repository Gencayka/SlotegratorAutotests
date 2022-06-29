package com.Chayka.requests.getClientToken;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope("singleton")
@ConfigurationProperties(prefix = "get-client-token")
public class GetClientTokenTestConfig {
    private String basePath;
    private String basicAuthenticationUsername;
    private Integer expectedPositiveResponseCode;
    private String expectedTokenType;
    private Integer expectedTokenExpirationTime;
    private String defaultGrantType;
    private String defaultScope;
}
