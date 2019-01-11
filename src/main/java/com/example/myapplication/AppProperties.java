package com.example.myapplication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class AppProperties {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String configuration;

    @Getter
    @Setter
    private String version;
}
