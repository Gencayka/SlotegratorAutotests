package com.Chayka.ui.uiElements;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum holds classes names for most of the Main page UI elements participating in testing
 */
@Getter
@AllArgsConstructor
public enum MainPageUIECN {
    PROFILE_BUTTON("nav-profile"),
    SIDE_BUTTONS("main-side-menu");

    private final String className;
}
