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

    /**
     * Checks if positive response HTTP-code corresponds to expected value
     * @return this tester
     */
    public abstract T checkPositiveResponseHttpCode();

    /**
     * Checks validation of the positive response body
     * @return this tester
     */
    public abstract T checkPositiveResponseValidation();

    /**
     * Deserializes positive response body from String to Java object.
     * Writes Java object to "testRequestPositiveResponse" field
     */
    protected abstract void deserializePositiveResponseBody();

    /**
     * Checks fields of negative response body
     * @param responseValues expected response values
     * @return this tester
     */
    public T checkNegativeResponseBody(@NotNull NegativeResponseValues responseValues) {
        deserializeNegativeResponseBody();
        checkNegativeResponseName(responseValues);
        checkNegativeResponseMessage(responseValues);
        checkNegativeResponseCode(responseValues);
        checkNegativeResponseStatus();
        return (T) this;
    }

    /**
     * Sends GET request to service and writes response to "testRequestResponse" fields
     * @param requestHeaders request headers, that being added to default headers
     * @return this tester
     */
    public T sendGetRequest(@NotNull Map<String, String> requestHeaders) {
        RequestSpecification localRequestSpecification = RestAssured.given().spec(defaultRequestSpecification);
        localRequestSpecification
                .baseUri(requestUri);
        testRequestResponse = sendGetRequest(requestHeaders, localRequestSpecification);
        logger.info("Response body: " + testRequestResponse.asString());
        return (T) this;
    }

    /**
     * Sends GET request to service and writes response to "testRequestResponse" fields.
     * Request has default headers
     * @return this tester
     */
    public T sendGetRequest() {
        return sendGetRequest(defaultHeaders);
    }

    /**
     * Sends GET request to service and writes response to "testRequestResponse" fields.
     * Request has bearer token in headers
     * @param token bearer token value
     * @return this tester
     */
    public T sendGetRequestWithBearerToken(@NotNull String token){
        Map<String,String> requestHeaders = new HashMap<>(defaultHeaders);
        requestHeaders.put(
                "Authorization",
                "Bearer " + token);
        return sendGetRequest(requestHeaders);
    }

    /**
     * Sends POST request to service and writes response to "testRequestResponse" fields.
     * @param requestHeaders request headers, that being added to default headers
     * @return this tester
     */
    public T sendPostRequest(@NotNull Map<String, String> requestHeaders,
                             @NotNull String requestBodyAsString) {
        RequestSpecification localRequestSpecification = RestAssured.given().spec(defaultRequestSpecification);
        localRequestSpecification
                .baseUri(requestUri);
        testRequestResponse = sendPostRequest(requestHeaders, localRequestSpecification, requestBodyAsString);
        logger.info("Response body: " + testRequestResponse.asString());
        return (T) this;
    }

    /**
     * Sends POST request to service and writes response to "testRequestResponse" fields.
     * Request has default headers
     * @return this tester
     */
    public T sendPostRequest(@NotNull String requestBodyAsString) {
        return sendPostRequest(defaultHeaders, requestBodyAsString);
    }

    /**
     * Sends POST request to service and writes response to "testRequestResponse" fields.
     * Request has basic authentication token based on username and password
     * @return this tester
     */
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

    /**
     * Sends POST request to service and writes response to "testRequestResponse" fields.
     * Request has bearer token in headers
     * @param token bearer token value
     * @return this tester
     */
    public T sendPostRequestWithBearerToken(@NotNull String token,
                                            @NotNull String requestBodyAsString) {
        Map<String,String> requestHeaders = new HashMap<>(defaultHeaders);
        requestHeaders.put(
                "Authorization",
                "Bearer " + token);
        return sendPostRequest(requestHeaders, requestBodyAsString);
    }

    /**
     * Checks if response HTTP-code corresponds to expected value
     * @return this tester
     */
    public T checkResponseHttpCode(int expectedHttpCode) {
        softAssertions
                .assertThat(testRequestResponse.statusCode())
                .describedAs("Response Http code check failed:")
                .isEqualTo(expectedHttpCode);
        softAssertions.assertAll();
        return (T) this;
    }

    /**
     * Checks if negative response HTTP-code corresponds to expected value
     * @param responseValues expected response values
     * @return this tester
     */
    public T checkNegativeResponseHttpCode(@NotNull NegativeResponseValues responseValues) {
        return checkResponseHttpCode(responseValues.getStatus());
    }

    /**
     * Checks validation of the negative response body
     * @return this tester
     */
    public T checkNegativeResponseValidation() {
        checkResponseValidation(jsonSchemas.getBadRequestResponseSchema());
        return (T) this;
    }

    /**
     * Calls test errors assertion
     */
    public void assertAll() {
        softAssertions.assertAll();
    }

    /**
     * Sends GET request to service and writes response to "testRequestResponse" fields
     * @return this tester
     */
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

    /**
     * Sends POST request to service and writes response to "testRequestResponse" fields
     * @return this tester
     */
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

    /**
     * Checks validation of the response body with JSON-schema
     */
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

    /**
     * Deserializes negative response body from String to Java object.
     * Writes Java object to "testRequestNegativeResponse" field
     */
    protected void deserializeNegativeResponseBody() {
        if (testRequestNegativeResponse == null) {
            testRequestNegativeResponse = ResponseBodyDeserializer.deserializeResponseBody(
                    testRequestResponse.asString(),
                    BadRequestResponse.class,
                    softAssertions);
        }
    }

    /**
     * Checks if "name" negative response body field corresponds to expected value
     * @param responseValues expected response values
     */
    protected void checkNegativeResponseName(@NotNull NegativeResponseValues responseValues) {
        softAssertions.assertThat(testRequestNegativeResponse.getName())
                .describedAs("Error name check failed:")
                .isEqualTo(responseValues.getName());
    }

    /**
     * Checks if "message" negative response body field corresponds to expected value
     * @param responseValues expected response values
     */
    protected void checkNegativeResponseMessage(@NotNull NegativeResponseValues responseValues) {
        softAssertions.assertThat(testRequestNegativeResponse.getMessage())
                .describedAs("Error message check failed:")
                .isEqualTo(responseValues.getMessage());
    }

    /**
     * Checks if "code" negative response body field corresponds to expected value
     * @param responseValues expected response values
     */
    protected void checkNegativeResponseCode(@NotNull NegativeResponseValues responseValues) {
        softAssertions.assertThat(testRequestNegativeResponse.getCode())
                .describedAs("Error code check failed:")
                .isEqualTo(responseValues.getCode());
    }

    /**
     * Checks if "status" negative response body field corresponds to expected value
     */
    protected void checkNegativeResponseStatus() {
        softAssertions.assertThat(testRequestNegativeResponse.getStatus())
                .describedAs("Error status check failed:")
                .isEqualTo(testRequestResponse.statusCode());
    }
}
