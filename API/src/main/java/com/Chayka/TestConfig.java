package com.Chayka;

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
@PropertySource("classpath:testConfig.properties")
public class TestConfig {
    private final String baseUrl;
    private final String getTokenPath;
    private final String registerPlayerPath;
    private final String getPlayersProfilePath;
    private final String basicAuthenticationUsername;

    private JsonSchema badRequestResponseSchema;

    @Getter
    private static TestConfig uniqueInstance;

    private TestConfig(@Value("${baseUrl}") String baseUrl,
                       @Value("${getTokenPath}") String getTokenPath,
                       @Value("${registerPlayerPath}") String registerPlayerPath,
                       @Value("${getPlayersProfilePath}") String getPlayersProfilePath,
                       @Value("${basicAuthenticationUsername}") String basicAuthenticationUsername,
                       @Value("classpath:BadRequestResponseSchema.json") Resource badRequestResponseSchema)
            throws IOException, ProcessingException {
        this.baseUrl = baseUrl;
        this.getTokenPath = getTokenPath;
        this.registerPlayerPath = registerPlayerPath;
        this.getPlayersProfilePath = getPlayersProfilePath;
        this.basicAuthenticationUsername = basicAuthenticationUsername;

        ObjectMapper mapper = new ObjectMapper();

        this.badRequestResponseSchema =
                JsonSchemaFactory.byDefault().getJsonSchema(
                        mapper.readTree(
                                StreamUtils.copyToString(badRequestResponseSchema.getInputStream(), StandardCharsets.UTF_8)));

        uniqueInstance = this;
    }
}
