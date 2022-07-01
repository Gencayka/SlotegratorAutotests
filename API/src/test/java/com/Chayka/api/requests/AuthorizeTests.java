package com.Chayka.api.requests;

import com.Chayka.api.requests.authorize.AuthorizeTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.Chayka.api.requests.authorize.AuthorizeNegativeResponseValues.*;

@DisplayName("(POST) Resource Owner Password Credentials Grant")
public class AuthorizeTests extends ApiTests<AuthorizeTester> {
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
    @DisplayName("Incorrect user credentials")
    public void test2() throws IOException {
        tester
                .sendRequest("user", "password")
                .checkNegativeResponseHttpCode(INCORRECT_USER_CREDENTIALS)
                .checkNegativeResponseValidation()
                .checkNegativeResponseBody(INCORRECT_USER_CREDENTIALS)
                .assertAll();
    }
}
