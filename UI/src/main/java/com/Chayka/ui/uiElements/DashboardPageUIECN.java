package com.Chayka.ui.uiElements;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum holds classes names for most of the Dashboard page UI elements participating in testing
 */
@Getter
@AllArgsConstructor
public enum DashboardPageUIECN {
    DEPOSIT_PANEL("col-sm-12");

    private final String className;
}
