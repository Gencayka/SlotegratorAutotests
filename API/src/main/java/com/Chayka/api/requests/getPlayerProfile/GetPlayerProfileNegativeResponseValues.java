package com.Chayka.api.requests.getPlayerProfile;

import com.Chayka.api.commons.NegativeResponseValues;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum holds Get Player Profile negative response expected values
 */
@Getter
@AllArgsConstructor
public enum GetPlayerProfileNegativeResponseValues implements NegativeResponseValues {
    PLAYER_NOT_FOUND("Not Found",
            "Object not found: %d",
            0,
            404);

    private final String name;
    private final String message;
    private final Integer code;
    private final Integer status;
}
