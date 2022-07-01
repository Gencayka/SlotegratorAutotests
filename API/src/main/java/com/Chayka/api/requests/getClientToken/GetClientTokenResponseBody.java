package com.Chayka.api.requests.getClientToken;

import com.Chayka.api.apiDtos.AccessTokenResponseDto;
import com.Chayka.api.commons.ResponseBody;
import lombok.Getter;
import lombok.Setter;

/**
 * Class to deserialize Get Client Token response body to
 */
@Getter
@Setter
public final class GetClientTokenResponseBody
    extends AccessTokenResponseDto
        implements ResponseBody {
}
