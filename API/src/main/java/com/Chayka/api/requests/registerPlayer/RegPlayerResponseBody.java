package com.Chayka.api.requests.registerPlayer;

import com.Chayka.api.apiDtos.PlayerInfoResponseDto;
import com.Chayka.api.commons.ResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class RegPlayerResponseBody
    extends PlayerInfoResponseDto
        implements ResponseBody {
}
