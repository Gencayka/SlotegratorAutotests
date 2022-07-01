package com.Chayka.api.requests.getPlayerProfile;

import com.Chayka.api.apiDtos.PlayerInfoResponseDto;
import com.Chayka.api.commons.ResponseBody;
import lombok.Getter;
import lombok.Setter;

/**
 * Class to deserialize Get Player Profile response body to
 */
@Getter
@Setter
public final class GetPlayerProfileResponseBody
    extends PlayerInfoResponseDto
        implements ResponseBody {
}
