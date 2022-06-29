package com.Chayka.commons;

import com.Chayka.TestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import io.restassured.RestAssured;
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

    protected Response testRequestResponse;
    protected B testRequestPositiveResponse;
    protected BadRequestResponse testRequestNegativeResponse;

    public RestApiTester() {
        logger = LoggerFactory.getLogger(RestApiTester.class.getSimpleName());

        defaultRequestSpecification = RestAssured.given();
        defaultHeaders = new HashMap<>();
        softAssertions = new SoftAssertions();
    }

    public abstract T checkPositiveResponseValidation();
    protected abstract void deserializePositiveResponseBody();
    protected abstract void checkNegativeResponseMessage(NegativeResponseValues responseValues);

    public T checkNegativeResponseBody(NegativeResponseValues responseValues){
        deserializeNegativeResponseBody();
        checkNegativeResponseStatus(responseValues);
        checkNegativeResponseMessage(responseValues);
        checkNegativeResponseErrors(responseValues);
        return (T) this;
    }

    public T sendGetRequest(Map<String, String> requestHeaders,
                            String requestUri) {
        RequestSpecification localRequestSpecification = RestAssured.given().spec(defaultRequestSpecification);
        localRequestSpecification
                .baseUri(requestUri);
        testRequestResponse = sendGetRequest(requestHeaders, localRequestSpecification);
        logger.info(testRequestResponse.asString());
        return (T) this;
    }

    public T sendGetRequest(String requestUri) {
        return sendGetRequest(defaultHeaders, requestUri);
    }

    public T checkResponseHttpCode(int expectedHttpCode) {
        softAssertions
                .assertThat(testRequestResponse.statusCode())
                .describedAs("Response Http code check failed:")
                .isEqualTo(expectedHttpCode);
        softAssertions.assertAll();
        return (T) this;
    }

    public T checkNegativeResponseValidation(){
        checkResponseValidation(TestConfig.getUniqueInstance().getBadRequestResponseSchema());
        return (T) this;
    }

    public void assertAll() {
        softAssertions.assertAll();
    }

    protected Response sendGetRequest(Map<String, String> requestHeaders,
                                      RequestSpecification requestSpecification) {
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

    protected void checkResponseValidation(JsonSchema responseJsonSchema){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ProcessingReport processingMessages = responseJsonSchema.validate(
                            mapper.readTree(testRequestResponse.asString()), false);
            if(!processingMessages.isSuccess()){
                softAssertions.fail("Response body validation check failed: " + processingMessages);
            }

        } catch (JsonProcessingException | ProcessingException exception){
            softAssertions.fail("Failed to check response body validation");
            logger.error(exception.getMessage(), exception);
            softAssertions.assertAll();
        }
    }

    protected <E> void checkIfListsAreEqual(@NotNull List<E> dbList,
                                            @NotNull List<E> responseList,
                                            @NotNull String nameOfField){
        List<E> dbListLocal = new ArrayList<>(dbList);
        ListIterator<E> dbListLocalIterator = dbListLocal.listIterator();

        List<E> responseListLocal = new ArrayList<>(responseList);
        ListIterator<E> responseListLocalIterator = responseListLocal.listIterator();

        while (dbListLocalIterator.hasNext()) {
            E dbListElement = dbListLocalIterator.next();
            while (responseListLocalIterator.hasNext()) {
                E responseListElement = responseListLocalIterator.next();
                if (responseListElement.equals(dbListElement)) {
                    dbListLocalIterator.remove();
                    responseListLocalIterator.remove();
                    break;
                }
            }
            responseListLocalIterator = responseListLocal.listIterator();
        }

        if (!dbListLocal.isEmpty()) {
            for (E dbListElement : dbListLocal) {
                softAssertions.fail(String.format("%s \"%s\" not found in response",
                        nameOfField, dbListElement));
            }
        }

        if (!responseListLocal.isEmpty()) {
            for (E responseListElement : responseListLocal) {
                softAssertions.fail(String.format("%s \"%s\" was found in response, but not in DB",
                        nameOfField, responseListElement));
            }
        }
    }

    protected void deserializeNegativeResponseBody(){
        if(testRequestNegativeResponse == null){
            testRequestNegativeResponse = ResponseBodyDeserializer.deserializeResponseBody(
                    testRequestResponse.asString(),
                    BadRequestResponse.class,
                    softAssertions);
        }
    }

    protected void checkNegativeResponseStatus(NegativeResponseValues responseValues){
        softAssertions.assertThat(testRequestNegativeResponse.getStatus())
                .describedAs("Error status check failed:")
                .isEqualTo(responseValues.getStatus());
    }

    protected void checkNegativeResponseErrors(NegativeResponseValues responseValues){
        softAssertions.assertThat(testRequestNegativeResponse.getErrors())
                .describedAs("Errors list check failed:")
                .isEqualTo(responseValues.getErrors());
    }
}
