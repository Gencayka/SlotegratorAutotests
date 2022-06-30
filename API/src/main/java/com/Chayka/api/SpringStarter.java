package com.Chayka.api;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Spring application starter
 */
@SpringBootApplication
public class SpringStarter {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringStarter.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}