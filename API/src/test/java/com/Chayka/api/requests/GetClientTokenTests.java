package com.Chayka.api.requests;

import com.Chayka.api.requests.getClientToken.GetClientTokenTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.Chayka.api.requests.getClientToken.GetClientTokenNegativeResponseValues.*;

@DisplayName("(POST) Get Client Token")
public class GetClientTokenTests extends ApiTests<GetClientTokenTester> {
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
    @DisplayName("Client authentication failed")
    public void test2() {
        tester
                .sendRequest("bebebe")
                .checkNegativeResponseHttpCode(CLIENT_AUTHENTICATION_FAILED)
                .checkNegativeResponseValidation()
                .checkNegativeResponseBody(CLIENT_AUTHENTICATION_FAILED)
                .assertAll();
    }
}
