package com.Chayka.api.requests.getClientToken;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetClientTokenRequestBody {
    @JsonProperty("grant_type")
    String grantType;
    String scope;
}
