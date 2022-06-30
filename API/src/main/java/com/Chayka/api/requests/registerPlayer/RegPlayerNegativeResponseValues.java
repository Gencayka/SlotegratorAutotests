package com.Chayka.api.requests.registerPlayer;

import com.Chayka.api.commons.NegativeResponseValues;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegPlayerNegativeResponseValues implements NegativeResponseValues {
    UNAUTHORIZED("Unauthorized",
            "Your request was made with invalid credentials.",
            0,
            401);

    private final String name;
    private final String message;
    private final Integer code;
    private final Integer status;
}
