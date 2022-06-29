package com.Chayka.commons;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BadRequestResponse implements ResponseBody{
    private String status;
    private String message;
    private String operationId;
    private List<String> errors;
}
