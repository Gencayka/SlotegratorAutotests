package com.Chayka.requests.getClientToken;

import com.Chayka.TestConfig;
import com.Chayka.commons.ResponseBodyDeserializer;
import com.Chayka.commons.RestApiTester;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Scope("prototype")
public final class GetClientTokenTester extends RestApiTester<GetClientTokenTester, GetClientTokenResponseBody> {
    private final String getTokenUri;

    public GetClientTokenTester(){
        super();
        getTokenUri = TestConfig.getUniqueInstance().getBaseUrl() + TestConfig.getUniqueInstance().getGetTokenPath();
    }

    public GetClientTokenTester sendPositiveRequest() {
        Map<String,String> requestHeaders = new HashMap<>();
        //TODO
        requestHeaders.put(
                "Authorization",
                //"Basic " + Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8)));
                "Basic ZnJvbnRfMmQ2YjBhODM5MTc0MmY1ZDc4OWQ3ZDkxNTc1NWUwOWU6");
        return sendPostRequest(requestHeaders, getTokenUri, "{\"grant_type\":\"client_credentials\",\"scope\":\"guest:default\"}");
    }

    public GetClientTokenTester sendRequest(String username) {
        Map<String,String> requestHeaders = new HashMap<>();
        requestHeaders.put(
                "Authorization",
                "Basic " + Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8)));
        return sendPostRequest(requestHeaders, getTokenUri, "{\"grant_type\":\"client_credentials\",\"scope\":\"guest:default\"}");
    }

    public GetClientTokenTester checkPositiveResponseHttpCode(){
        return checkResponseHttpCode(GetClientTokenTestConfig.getUniqueInstance().getExpectedPositiveResponseCode());
    }

    public GetClientTokenTester checkPositiveResponseValidation(){
        checkResponseValidation(TestConfig.getUniqueInstance().getAccessTokenResponseSchema());
        return this;
    }

    public GetClientTokenTester checkPositiveResponseBody(){
        deserializePositiveResponseBody();
        checkTokenType();
        checkTokenExpirationTime();
        checkAccessToken();
        checkRefreshToken();
        return this;
    }

    public String getToken(){
        logger.info("Getting client token");
        sendPositiveRequest();
        checkPositiveResponseHttpCode();
        return testRequestResponse.then().extract().path("access_token");
    }

    private void checkTokenType() {
        softAssertions.assertThat(testRequestPositiveResponse.getTokenType())
                .describedAs("Token type check failed:")
                .isEqualTo(GetClientTokenTestConfig.getUniqueInstance().getExpectedTokenType());
    }

    private void checkTokenExpirationTime() {
        softAssertions.assertThat(testRequestPositiveResponse.getExpiresIn())
                .describedAs("Token expiration time type check failed:")
                .isEqualTo(GetClientTokenTestConfig.getUniqueInstance().getExpectedTokenExpirationTime());
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
    protected void deserializePositiveResponseBody(){
        if(testRequestPositiveResponse == null){
            testRequestPositiveResponse = ResponseBodyDeserializer.deserializeResponseBody(
                            testRequestResponse.asString(),
                            GetClientTokenResponseBody.class, softAssertions);
        }
    }
}
