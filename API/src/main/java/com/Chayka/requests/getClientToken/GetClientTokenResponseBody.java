package com.Chayka.requests.getClientToken;

import com.Chayka.apiDtos.AccessTokenResponseDto;
import com.Chayka.commons.ResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class GetClientTokenResponseBody
    extends AccessTokenResponseDto
        implements ResponseBody {
}
