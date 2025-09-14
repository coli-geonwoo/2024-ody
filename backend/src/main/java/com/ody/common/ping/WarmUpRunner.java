package com.ody.common.ping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarmUpRunner {

    private final ThreadPoolTaskExecutor executor;

    public WarmUpRunner(
            @Qualifier("routeTimeCallExecutor") ThreadPoolTaskExecutor executor
    ) {
        this.executor = executor;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                executor.execute(() -> {
                    log.info("WarmUpRunner run start");
                });
            } catch (Exception ignored) {
            }
        }
    }
}

