package com.Chayka.ui.uiElements;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum holds classes names for most of the Admin login page UI elements participating in testing
 */
@Getter
@AllArgsConstructor
public enum AdminLoginPageUIECN {

    INPUT_FORM("form-control");

    private final String className;
}
