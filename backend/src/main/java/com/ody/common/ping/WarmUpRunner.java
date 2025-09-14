package com.ody.common.ping;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WarmUpRunner implements ApplicationRunner {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void run(ApplicationArguments args) {
        for (int i = 0; i < 50; i++) {
            try {
                restTemplate.getForObject("http://localhost:8080/health", String.class);
            } catch (Exception ignored) {}
        }
    }
}

