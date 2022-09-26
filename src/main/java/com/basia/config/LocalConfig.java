package com.basia.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalConfig {

    String url;
    String adminUsername;
    String adminPassword;
}
