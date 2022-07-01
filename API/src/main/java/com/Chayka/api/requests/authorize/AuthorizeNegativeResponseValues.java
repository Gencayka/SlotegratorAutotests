package com.Chayka.api.requests.authorize;

import com.Chayka.api.commons.NegativeResponseValues;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum holds Authorize negative response expected values
 */
@Getter
@AllArgsConstructor
public enum AuthorizeNegativeResponseValues implements NegativeResponseValues {
    CLIENT_AUTHENTICATION_FAILED("Unauthorized",
            "Client authentication failed",
            4,
            401),
    INCORRECT_USER_CREDENTIALS("Unauthorized",
            "The user credentials were incorrect.",
            6,
            401);

    private final String name;
    private final String message;
    private final Integer code;
    private final Integer status;
}
