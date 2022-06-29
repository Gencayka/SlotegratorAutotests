package com.Chayka.requests.authorize;

import com.Chayka.JsonSchemas;
import com.Chayka.TestConfig;
import com.Chayka.commons.ResponseBodyDeserializer;
import com.Chayka.commons.RestApiTester;
import com.Chayka.requests.getClientToken.GetClientTokenRequestBody;
import com.Chayka.requests.getClientToken.GetClientTokenResponseBody;
import com.Chayka.requests.getClientToken.GetClientTokenTestConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public final class AuthorizeTester extends RestApiTester<AuthorizeTester, AuthorizeResponseBody> {
    private final TestConfig testConfig;
    //private final JsonSchemas jsonSchemas;
    private final AuthorizeTestConfig authorizeTestConfig;

    public AuthorizeTester(@Autowired TestConfig testConfig,
                           @Autowired JsonSchemas jsonSchemas,
                           @Autowired AuthorizeTestConfig authorizeTestConfig) {
        super(jsonSchemas, testConfig.getBaseUrl() + authorizeTestConfig.getBasePath());
        this.testConfig = testConfig;
        //this.jsonSchemas = jsonSchemas;
        this.authorizeTestConfig = authorizeTestConfig;
    }

    public AuthorizeTester sendRequest(String grantType,
                                       String username,
                                       String password)
            throws IOException {
        Map<String, String> requestHeaders = new HashMap<>();
        //TODO
        requestHeaders.put(
                "Authorization",
                //"Basic " + Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8)));
                "Basic ZnJvbnRfMmQ2YjBhODM5MTc0MmY1ZDc4OWQ3ZDkxNTc1NWUwOWU6");

        AuthorizeRequestBody requestBody = AuthorizeRequestBody.builder()
                .grantType(grantType)
                .username(username)
                .password(password)
                .build();
        String requestBodyAsString = mapper.writeValueAsString(requestBody);

        return sendPostRequest(requestHeaders, requestBodyAsString);
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
