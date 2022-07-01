package com.Chayka.api.commons;

import java.util.List;

/**
 * Interface for *ResponseBody classes,
 * that are required to deserialize response body to.
 * Specifically for bodies in array form
 */
public interface ResponseArrayBody <T> extends ResponseBody {
    List<T> getElements();
    void setElements(List<T> elements);
}
