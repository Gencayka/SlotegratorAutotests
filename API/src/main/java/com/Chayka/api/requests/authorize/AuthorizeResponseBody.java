package com.Chayka.api.requests.authorize;

import com.Chayka.api.apiDtos.AccessTokenResponseDto;
import com.Chayka.api.commons.ResponseBody;
import lombok.Getter;
import lombok.Setter;

/**
 * Class to deserialize Authorize response body to
 */
@Getter
@Setter
public final class AuthorizeResponseBody
    extends AccessTokenResponseDto
        implements ResponseBody {
}
