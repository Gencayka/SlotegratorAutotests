package com.Chayka.requests.registerPlayer;

import com.Chayka.JsonSchemas;
import com.Chayka.TestConfig;
import com.Chayka.commons.ResponseBodyDeserializer;
import com.Chayka.commons.RestApiTester;
import com.Chayka.requests.getClientToken.GetClientTokenTester;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
public final class RegPlayerTester extends RestApiTester<RegPlayerTester, RegPlayerResponseBody> {
    private final TestConfig testConfig;
    private final JsonSchemas jsonSchemas;
    private final RegPlayerTestConfig regPlayerTestConfig;

    private final GetClientTokenTester getClientTokenTester;

    private RegPlayerRequestBody requestBody;

    public RegPlayerTester(@Autowired TestConfig testConfig,
                           @Autowired JsonSchemas jsonSchemas,
                           @Autowired RegPlayerTestConfig regPlayerTestConfig,
                           @Autowired GetClientTokenTester getClientTokenTester){
        super(testConfig.getBaseUrl() + regPlayerTestConfig.getBasePath());
        this.testConfig = testConfig;
        this.jsonSchemas = jsonSchemas;
        this.regPlayerTestConfig = regPlayerTestConfig;

        this.getClientTokenTester = getClientTokenTester;
    }

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
        checkResponseValidation(JsonSchemas.getUniqueInstance().getPlayerInfoResponseSchema());
        return this;
    }

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
