package com.Chayka.requests.getClientToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
@Component
@Scope("singleton")
@PropertySource("classpath:requests/getClientToken/getClientTokenTestConfig.properties")
public final class GetClientTokenTestConfig {
    private final String basePath;
    private final String basicAuthenticationUsername;
    private final Integer expectedPositiveResponseCode;
    private final String expectedTokenType;
    private final Integer expectedTokenExpirationTime;

    @Getter
    private static GetClientTokenTestConfig uniqueInstance;

    private GetClientTokenTestConfig(@Value("${basePath}") String basePath,
                                     @Value("${basicAuthenticationUsername}") String basicAuthenticationUsername,
                                     @Value("${expectedPositiveResponseCode}") Integer expectedPositiveResponseCode,
                                     @Value("${expectedTokenType}") String expectedTokenType,
                                     @Value("${expectedTokenExpirationTime}") Integer expectedTokenExpirationTime) {
        this.basePath = basePath;
        this.basicAuthenticationUsername = basicAuthenticationUsername;
        this.expectedPositiveResponseCode = expectedPositiveResponseCode;
        this.expectedTokenType = expectedTokenType;
        this.expectedTokenExpirationTime = expectedTokenExpirationTime;

        uniqueInstance = this;
    }
}
