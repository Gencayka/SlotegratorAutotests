package com.Chayka.api.commons;

import com.Chayka.api.JsonSchemas;
import com.Chayka.api.apiDtos.BadRequestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Class for testing services by sending REST-requests
 * <br>Abstract class, each request has its own child class
 */
public abstract class RestApiTester<T extends RestApiTester<T, B>, B extends ResponseBody> {
    protected final Logger logger;

    protected final RequestSpecification defaultRequestSpecification;
    protected final Map<String, String> defaultHeaders;
    protected final SoftAssertions softAssertions;
    protected final ObjectMapper mapper;

    protected final JsonSchemas jsonSchemas;
    protected String requestUri;
    protected Response testRequestResponse;
    protected B testRequestPositiveResponse;
    protected BadRequestResponse testRequestNegativeResponse;

    public RestApiTester(@NotNull JsonSchemas jsonSchemas,
                         @NotNull String requestUri) {
        this.logger = LoggerFactory.getLogger(RestApiTester.class.getSimpleName());

        this.defaultRequestSpecification = RestAssured.given()
                .contentType(ContentType.JSON)
                .baseUri(requestUri);
        this.defaultHeaders = new HashMap<>();
        this.softAssertions = new SoftAssertions();
        this.mapper = new ObjectMapper();

        this.jsonSchemas = jsonSchemas;
        this.requestUri = requestUri;
    }

    public abstract T checkPositiveResponseValidation();

    protected abstract void deserializePositiveResponseBody();

    public T checkNegativeResponseBody(@NotNull NegativeResponseValues responseValues) {
        deserializeNegativeResponseBody();
        checkNegativeResponseName(responseValues);
        checkNegativeResponseMessage(responseValues);
        checkNegativeResponseCode(responseValues);
        checkNegativeResponseStatus();
        return (T) this;
    }

    public T sendGetRequest(@NotNull Map<String, String> requestHeaders) {
        RequestSpecification localRequestSpecification = RestAssured.given().spec(defaultRequestSpecification);
        localRequestSpecification
                .baseUri(requestUri);
        testRequestResponse = sendGetRequest(requestHeaders, localRequestSpecification);
        logger.info("Response body: " + testRequestResponse.asString());
        return (T) this;
    }

    public T sendGetRequest() {
        return sendGetRequest(defaultHeaders);
    }

    public T sendGetRequestWithBearerToken(@NotNull String token){
        Map<String,String> requestHeaders = new HashMap<>(defaultHeaders);
        requestHeaders.put(
                "Authorization",
                "Bearer " + token);
        return sendGetRequest(requestHeaders);
    }

    public T sendPostRequest(@NotNull Map<String, String> requestHeaders,
                             @NotNull String requestBodyAsString) {
        RequestSpecification localRequestSpecification = RestAssured.given().spec(defaultRequestSpecification);
        localRequestSpecification
                .baseUri(requestUri);
        testRequestResponse = sendPostRequest(requestHeaders, localRequestSpecification, requestBodyAsString);
        logger.info("Response body: " + testRequestResponse.asString());
        return (T) this;
    }

    public T sendPostRequest(@NotNull String requestBodyAsString) {
        return sendPostRequest(defaultHeaders, requestBodyAsString);
    }

    public T sendPostRequestWithBasicAuth(@NotNull String username,
                                          @NotNull String password,
                             @NotNull String requestBodyAsString) {
        String valueToEncode = username + ":" + password;
        Map<String,String> requestHeaders = new HashMap<>(defaultHeaders);
        requestHeaders.put(
                "Authorization",
                "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes()));
        return sendPostRequest(requestHeaders, requestBodyAsString);
    }

    public T sendPostRequestWithBearerToken(@NotNull String token,
                                            @NotNull String requestBodyAsString) {
        Map<String,String> requestHeaders = new HashMap<>(defaultHeaders);
        requestHeaders.put(
                "Authorization",
                "Bearer " + token);
        return sendPostRequest(requestHeaders, requestBodyAsString);
    }

    public T checkResponseHttpCode(int expectedHttpCode) {
        softAssertions
                .assertThat(testRequestResponse.statusCode())
                .describedAs("Response Http code check failed:")
                .isEqualTo(expectedHttpCode);
        softAssertions.assertAll();
        return (T) this;
    }

    public T checkNegativeResponseHttpCode(@NotNull NegativeResponseValues responseValues) {
        return checkResponseHttpCode(responseValues.getStatus());
    }

    public T checkNegativeResponseValidation() {
        checkResponseValidation(jsonSchemas.getBadRequestResponseSchema());
        return (T) this;
    }

    public void assertAll() {
        softAssertions.assertAll();
    }

    protected Response sendGetRequest(@NotNull Map<String, String> requestHeaders,
                                      @NotNull RequestSpecification requestSpecification) {
        RequestSpecification localRequestSpecification = RestAssured.given().spec(requestSpecification);
        for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
            if (header.getValue() != null) {
                localRequestSpecification.header(header.getKey(), header.getValue());
            }
        }

        return localRequestSpecification
                .and()
                .when()
                .get()
                .then()
                .extract().response();
    }

    protected Response sendPostRequest(@NotNull Map<String, String> requestHeaders,
                                       @NotNull RequestSpecification requestSpecification,
                                       @NotNull String requestBodyAsString) {
        RequestSpecification requestSpecificationWithHeaders = RestAssured.given().spec(requestSpecification);
        for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
            if (header.getValue() != null) {
                requestSpecificationWithHeaders.header(header.getKey(), header.getValue());
            }
        }

        return requestSpecificationWithHeaders
                .and()
                .body(requestBodyAsString)
                .when()
                .post()
                .then()
                .extract().response();
    }

    protected void checkResponseValidation(@NotNull JsonSchema responseJsonSchema) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ProcessingReport processingMessages = responseJsonSchema.validate(
                    mapper.readTree(testRequestResponse.asString()), false);
            if (!processingMessages.isSuccess()) {
                softAssertions.fail("Response body validation check failed: " + processingMessages);
            }

        } catch (JsonProcessingException | ProcessingException exception) {
            softAssertions.fail("Failed to check response body validation");
            logger.error(exception.getMessage(), exception);
            softAssertions.assertAll();
        }
    }

    protected void deserializeNegativeResponseBody() {
        if (testRequestNegativeResponse == null) {
            testRequestNegativeResponse = ResponseBodyDeserializer.deserializeResponseBody(
                    testRequestResponse.asString(),
                    BadRequestResponse.class,
                    softAssertions);
        }
    }

    protected void checkNegativeResponseName(@NotNull NegativeResponseValues responseValues) {
        softAssertions.assertThat(testRequestNegativeResponse.getName())
                .describedAs("Error name check failed:")
                .isEqualTo(responseValues.getName());
    }

    protected void checkNegativeResponseMessage(@NotNull NegativeResponseValues responseValues) {
        softAssertions.assertThat(testRequestNegativeResponse.getMessage())
                .describedAs("Error message check failed:")
                .isEqualTo(responseValues.getMessage());
    }

    protected void checkNegativeResponseCode(@NotNull NegativeResponseValues responseValues) {
        softAssertions.assertThat(testRequestNegativeResponse.getCode())
                .describedAs("Error status check failed:")
                .isEqualTo(responseValues.getCode());
    }

    protected void checkNegativeResponseStatus() {
        softAssertions.assertThat(testRequestNegativeResponse.getStatus())
                .describedAs("Error status check failed:")
                .isEqualTo(testRequestResponse.statusCode());
    }
}
