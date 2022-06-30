package com.Chayka.api.commons;

import java.util.List;

public interface ResponseArrayBody <T> extends ResponseBody {
    List<T> getElements();
    void setElements(List<T> elements);
}
