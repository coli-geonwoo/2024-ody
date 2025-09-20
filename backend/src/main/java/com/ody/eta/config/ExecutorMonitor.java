package com.ody.eta.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
@EnableScheduling
public class ExecutorMonitor {

    private final ThreadPoolTaskExecutor executor;

    public ExecutorMonitor(@Qualifier("routeTimeCallExecutor") ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    @Scheduled(fixedRate = 1000) // 10ms마다 실행
    public void logExecutorStatus() {
        var pool = executor.getThreadPoolExecutor();

        log.info("[ExecutorStatus] PoolSize={}, ActiveCount={}, CompletedTaskCount={}, QueueSize={}",
                pool.getPoolSize(),
                pool.getActiveCount(), //실행중인 작업 수
                pool.getCompletedTaskCount(), //완료 한 작업
                pool.getQueue().size()); //대기 중인 작업
    }

    @PostConstruct
    void init() {
        var pool = executor.getThreadPoolExecutor();

        for (int i = 0; i < executor.getCorePoolSize(); i++) {
            pool.submit(() -> {
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            });
        }
    }
}

