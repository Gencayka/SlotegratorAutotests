package com.Chayka.apiDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerInfoResponseDto {
    private Integer id;
    @JsonProperty("country_id")
    private String countryId;
    @JsonProperty("timezone_id")
    private String timezoneId;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String gender;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String birthdate;
    @JsonProperty("bonuses_allowed")
    private Boolean bonusesAllowed;
    @JsonProperty("is_verified")
    private Boolean isVerified;
}
