package com.Chayka.requests.getClientToken;

import com.Chayka.commons.NegativeResponseValues;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
