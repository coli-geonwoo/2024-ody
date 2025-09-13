package com.ody.route.service;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "testodsay")
public class OdySayProperties {

    private final String[] apiKeys;

    public OdySayProperties(String[] apiKeys) {
        this.apiKeys = apiKeys;
    }

    public String getIndexof(int index) {
        return apiKeys[index % apiKeys.length];
    }
}
