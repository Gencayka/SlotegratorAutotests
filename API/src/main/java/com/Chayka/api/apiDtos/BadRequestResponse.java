package com.Chayka.api.apiDtos;

import com.Chayka.api.commons.ResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestResponse implements ResponseBody {
    private String name;
    private String message;
    private Integer code;
    private Integer status;
}
