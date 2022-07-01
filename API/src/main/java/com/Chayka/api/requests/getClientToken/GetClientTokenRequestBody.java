package com.Chayka.api.requests.getClientToken;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

/**
 * Class to build Get Client Token request body
 */
@Value
@Builder
public class GetClientTokenRequestBody {
    @JsonProperty("grant_type")
    String grantType;
    String scope;
}
