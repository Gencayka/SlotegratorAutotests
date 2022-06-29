package com.Chayka.requests.authorize;

import com.Chayka.apiDtos.AccessTokenResponseDto;
import com.Chayka.commons.ResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class AuthorizeResponseBody
    extends AccessTokenResponseDto
        implements ResponseBody {
}
