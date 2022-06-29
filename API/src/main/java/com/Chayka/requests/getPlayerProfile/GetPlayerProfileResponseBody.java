package com.Chayka.requests.getPlayerProfile;

import com.Chayka.apiDtos.PlayerInfoResponseDto;
import com.Chayka.commons.ResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class GetPlayerProfileResponseBody
    extends PlayerInfoResponseDto
        implements ResponseBody {
}
