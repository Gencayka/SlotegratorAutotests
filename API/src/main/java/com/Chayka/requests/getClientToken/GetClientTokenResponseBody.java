package com.Chayka.requests.getClientToken;

import com.Chayka.apiDtos.AccessTokenRequestDto;
import com.Chayka.commons.ResponseBody;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class GetClientTokenResponseBody
    extends AccessTokenRequestDto
        implements ResponseBody {
}
