package com.Chayka.api.requests.registerPlayer;

import com.Chayka.api.JsonSchemas;
import com.Chayka.api.TestConfig;
import com.Chayka.api.commons.ResponseBodyDeserializer;
import com.Chayka.api.commons.RestApiTester;
import com.Chayka.api.requests.getClientToken.GetClientTokenTester;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Class for testing the Register Player request
 */
@Component
@Scope("prototype")
public final class RegPlayerTester extends RestApiTester<RegPlayerTester, RegPlayerResponseBody> {
    private final RegPlayerTestConfig regPlayerTestConfig;

    private final GetClientTokenTester getClientTokenTester;

    private RegPlayerRequestBody requestBody;

    public RegPlayerTester(@Autowired TestConfig testConfig,
                           @Autowired JsonSchemas jsonSchemas,
                           @Autowired RegPlayerTestConfig regPlayerTestConfig,
                           @Autowired GetClientTokenTester getClientTokenTester){
        super(jsonSchemas, testConfig.getBaseUrl() + regPlayerTestConfig.getBasePath());
        this.regPlayerTestConfig = regPlayerTestConfig;

        this.getClientTokenTester = getClientTokenTester;
    }

    /**
     * Sends request to register new player with parameters generated with player username.
     * Request will return positive response, if DB doesn't contain a player with the same username yet
     * @return this tester
     * @throws IOException
     */
    public RegPlayerTester sendPositiveRequest(@NotNull String playerUsername) throws IOException {
        requestBody = RegPlayerRequestBody.builder()
                .username(playerUsername)
                .passwordChange(regPlayerTestConfig.getDefaultPassword())
                .passwordRepeat(regPlayerTestConfig.getDefaultPassword())
                .email(playerUsername + regPlayerTestConfig.getDefaultEmailAnnex())
                .name(playerUsername + regPlayerTestConfig.getDefaultNameAnnex())
                .surname(playerUsername + regPlayerTestConfig.getDefaultSurnameAnnex())
                .build();
        String requestBodyAsString = mapper.writeValueAsString(requestBody);

        return sendPostRequestWithBearerToken(getClientTokenTester.getToken(), requestBodyAsString);
    }

    public RegPlayerTester checkPositiveResponseHttpCode(){
        return checkResponseHttpCode(regPlayerTestConfig.getExpectedPositiveResponseCode());
    }

    public RegPlayerTester checkPositiveResponseValidation(){
        checkResponseValidation(jsonSchemas.getPlayerInfoResponseSchema());
        return this;
    }

    /**
     * Checks if positive response body fields corresponds to expected values
     * @return this tester
     */
    public RegPlayerTester checkPositiveResponseBody(){
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

    private void checkPlayerId(){
        softAssertions.assertThat(testRequestPositiveResponse.getId())
                .describedAs("ID check failed:")
                .isNotNull();
    }

    private void checkCountryId(){
        softAssertions.assertThat(testRequestPositiveResponse.getCountryId())
                .describedAs("Country ID check failed:")
                .isNull();
    }

    private void checkTimezoneId(){
        softAssertions.assertThat(testRequestPositiveResponse.getTimezoneId())
                .describedAs("Timezone ID check failed:")
                .isNull();
    }

    private void checkUsername(){
        softAssertions.assertThat(testRequestPositiveResponse.getUsername())
                .describedAs("Username check failed:")
                .isEqualTo(requestBody.getUsername());
    }

    private void checkEmail(){
        softAssertions.assertThat(testRequestPositiveResponse.getEmail())
                .describedAs("Email check failed:")
                .isEqualTo(requestBody.getEmail());
    }

    private void checkName(){
        softAssertions.assertThat(testRequestPositiveResponse.getName())
                .describedAs("Name check failed:")
                .isEqualTo(requestBody.getName());
    }

    private void checkSurname(){
        softAssertions.assertThat(testRequestPositiveResponse.getSurname())
                .describedAs("Surname check failed:")
                .isEqualTo(requestBody.getSurname());
    }

    private void checkGender(){
        softAssertions.assertThat(testRequestPositiveResponse.getGender())
                .describedAs("Gender check failed:")
                .isNull();
    }

    private void checkPhoneNumber(){
        softAssertions.assertThat(testRequestPositiveResponse.getPhoneNumber())
                .describedAs("Phone number check failed:")
                .isNull();
    }

    private void checkBirthdate(){
        softAssertions.assertThat(testRequestPositiveResponse.getBirthdate())
                .describedAs("Birthdate check failed:")
                .isNull();
    }

    private void checkBonusesAllowance(){
        softAssertions.assertThat(testRequestPositiveResponse.getBonusesAllowed())
                .describedAs("Bonuses allowance check failed:")
                .isTrue();
    }

    private void checkVerification(){
        softAssertions.assertThat(testRequestPositiveResponse.getIsVerified())
                .describedAs("Verification check failed:")
                .isFalse();
    }

    @Override
    protected void deserializePositiveResponseBody(){
        if(testRequestPositiveResponse == null){
            testRequestPositiveResponse = ResponseBodyDeserializer.deserializeResponseBody(
                            testRequestResponse.asString(),
                            RegPlayerResponseBody.class, softAssertions);
        }
    }
}
