package com.Chayka.requests.registerPlayer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope("singleton")
@ConfigurationProperties(prefix = "reg-player")
public final class RegPlayerTestConfig {
    private String basePath;
    private Integer expectedPositiveResponseCode;
    private String defaultPassword;
    private String defaultEmailAnnex;
    private String defaultNameAnnex;
    private String defaultSurnameAnnex;
}
