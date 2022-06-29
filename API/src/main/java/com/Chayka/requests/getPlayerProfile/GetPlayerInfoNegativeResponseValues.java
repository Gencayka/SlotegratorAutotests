package com.Chayka.requests.getPlayerProfile;

import com.Chayka.commons.NegativeResponseValues;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GetPlayerInfoNegativeResponseValues implements NegativeResponseValues {
    PLAYER_NOT_FOUND("Not Found",
            "Object not found: %d",
            0,
            404);

    private final String name;
    private final String message;
    private final Integer code;
    private final Integer status;
}
