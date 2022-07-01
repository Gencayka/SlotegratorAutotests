package com.Chayka.api.requests.getClientToken;

import com.Chayka.api.JsonSchemas;
import com.Chayka.api.TestConfig;
import com.Chayka.api.commons.ResponseBodyDeserializer;
import com.Chayka.api.commons.RestApiTester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Scope("prototype")
public final class GetClientTokenTester
        extends RestApiTester<GetClientTokenTester, GetClientTokenResponseBody> {
    private final TestConfig testConfig;
    private final GetClientTokenTestConfig getClientTokenTestConfig;

    public GetClientTokenTester(@Autowired TestConfig testConfig,
                                @Autowired JsonSchemas jsonSchemas,
                                @Autowired GetClientTokenTestConfig getClientTokenTestConfig) {
        super(jsonSchemas, testConfig.getBaseUrl() + getClientTokenTestConfig.getBasePath());
        this.testConfig = testConfig;
        this.getClientTokenTestConfig = getClientTokenTestConfig;
    }

    public GetClientTokenTester sendPositiveRequest() throws IOException {
        return sendRequest(getClientTokenTestConfig.getBasicAuthenticationUsername(), getClientTokenTestConfig.getBasicAuthenticationPassword());
    }

    public GetClientTokenTester sendRequest(@NotNull String username,
                                            @NotNull String password) throws IOException{
        GetClientTokenRequestBody requestBody = GetClientTokenRequestBody.builder()
                .grantType(getClientTokenTestConfig.getDefaultGrantType())
                .scope(getClientTokenTestConfig.getDefaultScope())
                .build();
        String requestBodyAsString = mapper.writeValueAsString(requestBody);

        return sendPostRequestWithBasicAuth(
                username,
                password,
                requestBodyAsString);
    }

    public GetClientTokenTester checkPositiveResponseHttpCode() {
        return checkResponseHttpCode(getClientTokenTestConfig.getExpectedPositiveResponseCode());
    }

    public GetClientTokenTester checkPositiveResponseValidation() {
        checkResponseValidation(jsonSchemas.getAccessTokenResponseSchema());
        return this;
    }

    public GetClientTokenTester checkPositiveResponseBody() {
        deserializePositiveResponseBody();
        checkTokenType();
        checkTokenExpirationTime();
        checkAccessToken();
        checkRefreshToken();
        return this;
    }

    public String getToken() throws IOException {
        logger.info("Getting client token");
        sendPositiveRequest();
        checkPositiveResponseHttpCode();
        return testRequestResponse.then().extract().path("access_token");
    }

    private void checkTokenType() {
        softAssertions.assertThat(testRequestPositiveResponse.getTokenType())
                .describedAs("Token type check failed:")
                .isEqualTo(getClientTokenTestConfig.getExpectedTokenType());
    }

    private void checkTokenExpirationTime() {
        softAssertions.assertThat(testRequestPositiveResponse.getExpiresIn())
                .describedAs("Token expiration time type check failed:")
                .isEqualTo(getClientTokenTestConfig.getExpectedTokenExpirationTime());
    }

    private void checkAccessToken() {
        softAssertions.assertThat(testRequestPositiveResponse.getAccessToken())
                .describedAs("Access token check failed:")
                .isNotNull();
    }

    private void checkRefreshToken() {
        softAssertions.assertThat(testRequestPositiveResponse.getRefreshToken())
                .describedAs("Refresh token check failed:")
                .isNull();
    }

    @Override
    protected void deserializePositiveResponseBody() {
        if (testRequestPositiveResponse == null) {
            testRequestPositiveResponse = ResponseBodyDeserializer.deserializeResponseBody(
                    testRequestResponse.asString(),
                    GetClientTokenResponseBody.class, softAssertions);
        }
    }
}
