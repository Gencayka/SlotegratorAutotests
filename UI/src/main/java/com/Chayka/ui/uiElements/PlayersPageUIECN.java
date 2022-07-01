package com.Chayka.ui.uiElements;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum holds classes names for most of the Players page UI elements participating in testing
 */
@Getter
@AllArgsConstructor
public enum PlayersPageUIECN {
    PLAYER_MANAGEMENT_HEADING("panel-heading"),
    COLUMN_BUTTON("sort-link");

    private final String className;
}
