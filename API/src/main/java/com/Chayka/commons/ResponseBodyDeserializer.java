package com.Chayka.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ResponseBodyDeserializer {
    private static final ObjectMapper mapper = new ObjectMapper();

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

    public static <T extends ResponseBody> T deserializeResponseBody(String responseBodyAsString,
                                                                     Class<T> responseBodyClass,
                                                                     SoftAssertions softAssertions) {
        return deserializeResponseBody(responseBodyAsString, responseBodyClass, softAssertions, mapper);
    }

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

    public static <RB extends ResponseArrayBody<RAE>, RAE> RB deserializeResponseArrayBody(String responseBodyAsString,
                                                                                           Class<RB> responseBodyClass,
                                                                                           Class<RAE> responseArrayElementClass,
                                                                                           SoftAssertions softAssertions) {
        return deserializeResponseArrayBody(responseBodyAsString, responseBodyClass, responseArrayElementClass, softAssertions, mapper);
    }
}
