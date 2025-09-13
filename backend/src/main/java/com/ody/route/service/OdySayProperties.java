package com.ody.route.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "testodsay")
public class OdySayProperties {

    private final List<String> apiKeys;

    public OdySayProperties(String[] apiKeys) {
        this.apiKeys = Stream.of(apiKeys)
                .map(key -> URLEncoder.encode(key, StandardCharsets.UTF_8))
                .toList();}

    public String getIndexof(int index) {
        return apiKeys.get(index % apiKeys.size());
    }
}
