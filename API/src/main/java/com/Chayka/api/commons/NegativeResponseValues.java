package com.Chayka.api.commons;

/**
 * Interface for *NegativeResponseValues enums,
 * that holds negative response expected values
 */
public interface NegativeResponseValues {
    String getName();
    String getMessage();
    Integer getCode();
    Integer getStatus();
}
