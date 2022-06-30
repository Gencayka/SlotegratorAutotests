package com.Chayka.apiDtos;

import com.Chayka.commons.ResponseBody;
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
