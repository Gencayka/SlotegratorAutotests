package com.Chayka.api.requests;

import com.Chayka.api.requests.registerPlayer.RegPlayerTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static com.Chayka.api.requests.registerPlayer.RegPlayerNegativeResponseValues.*;

@DisplayName("(POST) Register a new player")
public class RegPlayerTests extends ApiTests<RegPlayerTester> {
    @DisplayName("Positive")
    @ParameterizedTest
    @ValueSource(strings = {
            "userAB",
            "userCD",
            "userEF"})
    public void test1(String playerUsername) throws IOException {
        tester
                .sendPositiveRequest(playerUsername)
                .checkPositiveResponseHttpCode()
                .checkPositiveResponseValidation()
                .assertAll();
    }

    @Test
    @DisplayName("Request with invalid credentials")
    public void test2() {
        tester
                .sendPostRequest("")
                .checkNegativeResponseHttpCode(UNAUTHORIZED)
                .checkNegativeResponseValidation()
                .checkNegativeResponseBody(UNAUTHORIZED)
                .assertAll();
    }
}
