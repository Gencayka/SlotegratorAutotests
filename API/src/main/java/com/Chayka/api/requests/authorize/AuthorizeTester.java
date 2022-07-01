package com.Chayka.api.requests.authorize;

import com.Chayka.api.JsonSchemas;
import com.Chayka.api.TestConfig;
import com.Chayka.api.commons.ResponseBodyDeserializer;
import com.Chayka.api.commons.RestApiTester;
import com.Chayka.api.requests.getClientToken.GetClientTokenTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public final class AuthorizeTester extends RestApiTester<AuthorizeTester, AuthorizeResponseBody> {
    private final TestConfig testConfig;
    private final AuthorizeTestConfig authorizeTestConfig;
    private final GetClientTokenTestConfig getClientTokenTestConfig;

    public AuthorizeTester(@Autowired TestConfig testConfig,
                           @Autowired JsonSchemas jsonSchemas,
                           @Autowired AuthorizeTestConfig authorizeTestConfig,
                           @Autowired GetClientTokenTestConfig getClientTokenTestConfig) {
        super(jsonSchemas, testConfig.getBaseUrl() + authorizeTestConfig.getBasePath());
        this.testConfig = testConfig;
        this.authorizeTestConfig = authorizeTestConfig;
        this.getClientTokenTestConfig = getClientTokenTestConfig;
    }

    public AuthorizeTester sendRequest(String grantType,
                                       String username,
                                       String password)
            throws IOException {
        AuthorizeRequestBody requestBody = AuthorizeRequestBody.builder()
                .grantType(grantType)
                .username(username)
                .password(password)
                .build();
        String requestBodyAsString = mapper.writeValueAsString(requestBody);

        return sendPostRequestWithBasicAuth(
                getClientTokenTestConfig.getBasicAuthenticationUsername(),
                getClientTokenTestConfig.getBasicAuthenticationPassword(),
                requestBodyAsString);
    }

    public AuthorizeTester sendRequest(String username,
                                       String password)
            throws IOException {
        return sendRequest(authorizeTestConfig.getDefaultGrantType(), username, password);
    }

    public AuthorizeTester sendPositiveRequest() throws IOException {
        return sendRequest(authorizeTestConfig.getDefaultGrantType(), authorizeTestConfig.getDefaultUsername(), authorizeTestConfig.getDefaultPassword());
    }

    public String getToken(String username,
                           String password) throws IOException {
        logger.info("Getting player token");
        sendRequest(authorizeTestConfig.getDefaultGrantType(), username, password);
        checkPositiveResponseHttpCode();
        return testRequestResponse.then().extract().path("access_token");
    }

    public String getDefaultPlayerToken() throws IOException {
        return getToken(authorizeTestConfig.getDefaultUsername(), authorizeTestConfig.getDefaultPassword());
    }

    public AuthorizeTester checkPositiveResponseHttpCode() {
        return checkResponseHttpCode(authorizeTestConfig.getExpectedPositiveResponseCode());
    }

    public AuthorizeTester checkPositiveResponseValidation() {
        checkResponseValidation(jsonSchemas.getAccessTokenResponseSchema());
        return this;
    }

    public AuthorizeTester checkPositiveResponseBody() {
        deserializePositiveResponseBody();
        checkTokenType();
        checkTokenExpirationTime();
        checkAccessToken();
        checkRefreshToken();
        return this;
    }

    private void checkTokenType() {
        softAssertions.assertThat(testRequestPositiveResponse.getTokenType())
                .describedAs("Token type check failed:")
                .isEqualTo(authorizeTestConfig.getExpectedTokenType());
    }

    private void checkTokenExpirationTime() {
        softAssertions.assertThat(testRequestPositiveResponse.getExpiresIn())
                .describedAs("Token expiration time type check failed:")
                .isEqualTo(authorizeTestConfig.getExpectedTokenExpirationTime());
    }

    private void checkAccessToken() {
        softAssertions.assertThat(testRequestPositiveResponse.getAccessToken())
                .describedAs("Access token check failed:")
                .isNotNull();
    }

    private void checkRefreshToken() {
        softAssertions.assertThat(testRequestPositiveResponse.getRefreshToken())
                .describedAs("Refresh token check failed:")
                .isNotNull();
    }

    @Override
    protected void deserializePositiveResponseBody() {
        if (testRequestPositiveResponse == null) {
            testRequestPositiveResponse = ResponseBodyDeserializer.deserializeResponseBody(
                    testRequestResponse.asString(),
                    AuthorizeResponseBody.class, softAssertions);
        }
    }
}
