package com.Chayka.api.requests.getPlayerProfile;

import com.Chayka.api.JsonSchemas;
import com.Chayka.api.TestConfig;
import com.Chayka.api.commons.NegativeResponseValues;
import com.Chayka.api.commons.ResponseBodyDeserializer;
import com.Chayka.api.commons.RestApiTester;
import com.Chayka.api.requests.authorize.AuthorizeTester;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Class for testing the Get Player Profile request
 */
@Component
@Scope("prototype")
public final class GetPlayerProfileTester extends RestApiTester<GetPlayerProfileTester, GetPlayerProfileResponseBody> {
    private final GetPlayerProfileTestConfig getPlayerProfileTestConfig;

    private final AuthorizeTester authorizeTester;

    private int playerId;

    public GetPlayerProfileTester(@Autowired TestConfig testConfig,
                                  @Autowired JsonSchemas jsonSchemas,
                                  @Autowired GetPlayerProfileTestConfig getPlayerProfileTestConfig,
                                  @Autowired AuthorizeTester authorizeTester) {
        super(jsonSchemas, testConfig.getBaseUrl() + getPlayerProfileTestConfig.getBasePath());
        this.getPlayerProfileTestConfig = getPlayerProfileTestConfig;

        this.authorizeTester = authorizeTester;
    }

    public GetPlayerProfileTester sendRequest(int playerId,
                                              String username,
                                              String password) throws IOException {
        this.playerId = playerId;
        requestUri = String.format(requestUri, playerId);
        return sendGetRequestWithBearerToken(authorizeTester.getToken(username, password));
    }

    public GetPlayerProfileTester sendPositiveRequest() throws IOException {
        requestUri = String.format(requestUri, getPlayerProfileTestConfig.getDefaultPlayerId());
        return sendGetRequestWithBearerToken(authorizeTester.getDefaultPlayerToken());
    }

    public GetPlayerProfileTester checkPositiveResponseHttpCode() {
        return checkResponseHttpCode(getPlayerProfileTestConfig.getExpectedPositiveResponseCode());
    }

    public GetPlayerProfileTester checkPositiveResponseValidation() {
        checkResponseValidation(jsonSchemas.getPlayerInfoResponseSchema());
        return this;
    }

    /**
     * Checks if positive response body fields corresponds to expected values
     * @return this tester
     */
    public GetPlayerProfileTester checkPositiveResponseBody() {
        deserializePositiveResponseBody();
        checkPlayerId();
        checkCountryId();
        checkTimezoneId();
        checkUsername();
        checkEmail();
        checkName();
        checkSurname();
        checkGender();
        checkPhoneNumber();
        checkBirthdate();
        checkBonusesAllowance();
        checkVerification();
        return this;
    }

    private void checkPlayerId() {
        softAssertions.assertThat(testRequestPositiveResponse.getId())
                .describedAs("ID check failed:")
                .isEqualTo(getPlayerProfileTestConfig.getDefaultPlayerId());
    }

    private void checkCountryId() {
        softAssertions.assertThat(testRequestPositiveResponse.getCountryId())
                .describedAs("Country ID check failed:")
                .isEqualTo(getPlayerProfileTestConfig.getDefaultCountryId());
    }

    private void checkTimezoneId() {
        softAssertions.assertThat(testRequestPositiveResponse.getTimezoneId())
                .describedAs("Timezone ID check failed:")
                .isEqualTo(getPlayerProfileTestConfig.getDefaultTimezoneId());
    }

    private void checkUsername() {
        softAssertions.assertThat(testRequestPositiveResponse.getUsername())
                .describedAs("Username check failed:")
                .isEqualTo(getPlayerProfileTestConfig.getDefaultUsername());
    }

    private void checkEmail() {
        softAssertions.assertThat(testRequestPositiveResponse.getEmail())
                .describedAs("Email check failed:")
                .isEqualTo(getPlayerProfileTestConfig.getDefaultEmail());
    }

    private void checkName() {
        softAssertions.assertThat(testRequestPositiveResponse.getName())
                .describedAs("Name check failed:")
                .isEqualTo(getPlayerProfileTestConfig.getDefaultName());
    }

    private void checkSurname() {
        softAssertions.assertThat(testRequestPositiveResponse.getSurname())
                .describedAs("Surname check failed:")
                .isEqualTo(getPlayerProfileTestConfig.getDefaultSurname());
    }

    private void checkGender() {
        softAssertions.assertThat(testRequestPositiveResponse.getGender())
                .describedAs("Gender check failed:")
                .isEqualTo(getPlayerProfileTestConfig.getDefaultGender());
    }

    private void checkPhoneNumber() {
        softAssertions.assertThat(testRequestPositiveResponse.getPhoneNumber())
                .describedAs("Phone number check failed:")
                .isEqualTo(getPlayerProfileTestConfig.getDefaultPhoneNumber());
    }

    private void checkBirthdate() {
        softAssertions.assertThat(testRequestPositiveResponse.getBirthdate())
                .describedAs("Birthdate check failed:")
                .isEqualTo(getPlayerProfileTestConfig.getDefaultBirthdate());
    }

    private void checkBonusesAllowance() {
        softAssertions.assertThat(testRequestPositiveResponse.getBonusesAllowed())
                .describedAs("Bonuses allowance check failed:")
                .isEqualTo(getPlayerProfileTestConfig.getDefaultBonusesAllowed());
    }

    private void checkVerification() {
        softAssertions.assertThat(testRequestPositiveResponse.getIsVerified())
                .describedAs("Verification check failed:")
                .isEqualTo(getPlayerProfileTestConfig.getDefaultIsVerified());
    }

    @Override
    protected void deserializePositiveResponseBody() {
        if (testRequestPositiveResponse == null) {
            testRequestPositiveResponse = ResponseBodyDeserializer.deserializeResponseBody(
                    testRequestResponse.asString(),
                    GetPlayerProfileResponseBody.class, softAssertions);
        }
    }

    @Override
    protected void checkNegativeResponseMessage(@NotNull NegativeResponseValues responseValues) {
        softAssertions.assertThat(testRequestNegativeResponse.getMessage())
                .describedAs("Error message check failed:")
                .isEqualTo(String.format(responseValues.getMessage(), playerId));
    }
}
