package com.Chayka.api.requests;

import com.Chayka.api.ApiTests;
import com.Chayka.api.requests.getPlayerProfile.GetPlayerProfileTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.Chayka.api.requests.getPlayerProfile.GetPlayerProfileNegativeResponseValues.*;

@DisplayName("(POST) Get a single player profile")
public class GetPlayerProfileTests extends ApiTests<GetPlayerProfileTester> {
    @Test
    @DisplayName("Positive")
    public void test1() throws IOException {
        tester
                .sendPositiveRequest()
                .checkPositiveResponseHttpCode()
                .checkPositiveResponseValidation()
                .checkPositiveResponseBody()
                .assertAll();
    }

    @Test
    @DisplayName("Attempt to get another player profile info")
    public void getPlayerInfoNegativeTest() throws IOException {
        tester
                .sendRequest(11914, "userAB", "NYf4@M4tgg==")
                .checkNegativeResponseHttpCode(PLAYER_NOT_FOUND)
                .checkNegativeResponseValidation()
                .checkNegativeResponseBody(PLAYER_NOT_FOUND)
                .assertAll();
    }
}
