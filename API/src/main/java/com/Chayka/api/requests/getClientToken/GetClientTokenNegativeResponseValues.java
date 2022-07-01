package com.Chayka.api.requests.getClientToken;

import com.Chayka.api.commons.NegativeResponseValues;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum holds Get Client Token negative response expected values
 */
@Getter
@AllArgsConstructor
public enum GetClientTokenNegativeResponseValues implements NegativeResponseValues {
    CLIENT_AUTHENTICATION_FAILED("Unauthorized",
            "Client authentication failed",
            4,
            401);

    private final String name;
    private final String message;
    private final Integer code;
    private final Integer status;
}
