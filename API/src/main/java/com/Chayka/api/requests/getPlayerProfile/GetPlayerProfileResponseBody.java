package com.Chayka.api.requests.getPlayerProfile;

import com.Chayka.api.apiDtos.PlayerInfoResponseDto;
import com.Chayka.api.commons.ResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class GetPlayerProfileResponseBody
    extends PlayerInfoResponseDto
        implements ResponseBody {
}
