package com.Chayka.api.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Class for JSON to Java objects deserialization
 */
public class ResponseBodyDeserializer {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Deserializes JSON response body from String to Java object
     * @param responseBodyClass class to deserialize response body to
     * @param softAssertions is required in case of deserialization fail
     * @return response body as Java object
     */
    public static <RB extends ResponseBody> RB deserializeResponseBody(String responseBodyAsString,
                                                                       Class<RB> responseBodyClass,
                                                                       SoftAssertions softAssertions,
                                                                       ObjectMapper mapper) {
        try {
            return mapper.readValue(responseBodyAsString, responseBodyClass);
        } catch (JsonProcessingException exception) {
            softAssertions.fail(String.format("Failed to deserialize response body\n%s\n%s", exception.getMessage(), exception));
            softAssertions.assertAll();
        }
        return null;
    }

    /**
     * Deserializes JSON response body from String to Java object
     * @param responseBodyClass class to deserialize response body to
     * @param softAssertions is required in case of deserialization fail
     * @return response body as Java object
     */
    public static <T extends ResponseBody> T deserializeResponseBody(String responseBodyAsString,
                                                                     Class<T> responseBodyClass,
                                                                     SoftAssertions softAssertions) {
        return deserializeResponseBody(responseBodyAsString, responseBodyClass, softAssertions, mapper);
    }

    /**
     * Deserializes JSON response body in array form from String to Java object
     * @param responseBodyClass class to deserialize response body to
     * @param responseArrayElementClass class to deserialize single element of the response body to
     * @param softAssertions is required in case of deserialization fail
     * @return response body as Java object
     */
    public static <RB extends ResponseArrayBody<RAE>, RAE> RB deserializeResponseArrayBody(String responseBodyAsString,
                                                                                           Class<RB> responseBodyClass,
                                                                                           Class<RAE> responseArrayElementClass,
                                                                                           SoftAssertions softAssertions,
                                                                                           ObjectMapper mapper) {
        try {
            RB responseAsObject = responseBodyClass.getDeclaredConstructor().newInstance();
            responseAsObject.setElements(mapper.readValue(
                    responseBodyAsString,
                    mapper.getTypeFactory().constructCollectionType(List.class, responseArrayElementClass)));
            return responseAsObject;
        } catch (JsonProcessingException | ClassCastException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
            softAssertions.fail(String.format("Failed to deserialize response body\n%s\n%s", exception.getMessage(), exception));
            softAssertions.assertAll();
        }
        return null;
    }

    /**
     * Deserializes JSON response body in array form from String to Java object
     * @param responseBodyClass class to deserialize response body to
     * @param responseArrayElementClass class to deserialize single element of the response body to
     * @param softAssertions is required in case of deserialization fail
     * @return response body as Java object
     */
    public static <RB extends ResponseArrayBody<RAE>, RAE> RB deserializeResponseArrayBody(String responseBodyAsString,
                                                                                           Class<RB> responseBodyClass,
                                                                                           Class<RAE> responseArrayElementClass,
                                                                                           SoftAssertions softAssertions) {
        return deserializeResponseArrayBody(responseBodyAsString, responseBodyClass, responseArrayElementClass, softAssertions, mapper);
    }
}
