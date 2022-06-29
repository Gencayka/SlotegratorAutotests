package com.Chayka.requests.registerPlayer;

import com.Chayka.apiDtos.AccessTokenResponseDto;
import com.Chayka.apiDtos.PlayerInfoResponseDto;
import com.Chayka.commons.ResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class RegPlayerResponseBody
    extends PlayerInfoResponseDto
        implements ResponseBody {
}
