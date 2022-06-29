package com.Chayka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
@Component
@Scope("singleton")
public class JsonSchemas {
    private final JsonSchema badRequestResponseSchema;
    private final JsonSchema accessTokenResponseSchema;
    private final JsonSchema playerInfoResponseSchema;

    @Getter
    private static JsonSchemas uniqueInstance;

    private JsonSchemas(@Value("classpath:schemas/BadRequestResponseSchema.json") Resource badRequestResponseSchema,
                        @Value("classpath:schemas/AccessTokenResponseSchema.json") Resource accessTokenResponseSchema,
                        @Value("classpath:schemas/PlayerInfoResponseSchema.json") Resource playerInfoResponseSchema)
            throws IOException, ProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        this.badRequestResponseSchema =
                JsonSchemaFactory.byDefault().getJsonSchema(mapper.readTree(
                        StreamUtils.copyToString(badRequestResponseSchema.getInputStream(), StandardCharsets.UTF_8)));
        this.accessTokenResponseSchema =
                JsonSchemaFactory.byDefault().getJsonSchema(mapper.readTree(
                        StreamUtils.copyToString(accessTokenResponseSchema.getInputStream(), StandardCharsets.UTF_8)));
        this.playerInfoResponseSchema =
                JsonSchemaFactory.byDefault().getJsonSchema(mapper.readTree(
                        StreamUtils.copyToString(playerInfoResponseSchema.getInputStream(), StandardCharsets.UTF_8)));

        uniqueInstance = this;
    }
}
