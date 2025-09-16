package com.ody.common.ping;

import com.ody.eta.dto.request.MateEtaRequest;
import com.ody.eta.service.EtaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarmUpRunner {

    private final ThreadPoolTaskExecutor executor;
    private final EtaService etaService;

    public WarmUpRunner(
            @Qualifier("routeTimeCallExecutor") ThreadPoolTaskExecutor executor,
            EtaService etaService
    ) {
        this.executor = executor;
        this.etaService = etaService;
    }

    public void run() {
        for (int i = 0; i < 50; i++) {
            try {
                executor.execute(() -> {
                    log.info("WarmUpRunner run start");
                });
            } catch (Exception ignored) {
            }
        }
    }
}

