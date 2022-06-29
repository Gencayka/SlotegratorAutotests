package com.Chayka.requests.getPlayerProfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope("singleton")
@ConfigurationProperties(prefix = "get-player-profile")
public final class GetPlayerProfileTestConfig {
    private String basePath;
    private Integer expectedPositiveResponseCode;
    private Integer defaultPlayerId;
    private String defaultCountryId;
    private String defaultTimezoneId;
    private String defaultUsername;
    private String defaultEmail;
    private String defaultName;
    private String defaultSurname;
    private String defaultGender;
    private String defaultPhoneNumber;
    private String defaultBirthdate;
    private Boolean defaultBonusesAllowed;
    private Boolean defaultIsVerified;
}
