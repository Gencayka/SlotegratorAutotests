package com.Chayka.requests.registerPlayer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegPlayerRequestBody {
    String username;
    @JsonProperty("password_change")
    String passwordChange;
    @JsonProperty("password_repeat")
    String passwordRepeat;
    String email;
    String name;
    String surname;

    //Request with any currency code isn't accepted for some reason
    //@JsonProperty("currency_code")
    //String currencyCode;
}
