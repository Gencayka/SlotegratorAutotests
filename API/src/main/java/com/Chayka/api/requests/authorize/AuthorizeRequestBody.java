package com.Chayka.api.requests.authorize;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

/**
 * Class to build Authorize request body
 */
@Value
@Builder
public class AuthorizeRequestBody {
    @JsonProperty("grant_type")
    String grantType;
    String username;
    String password;
}
