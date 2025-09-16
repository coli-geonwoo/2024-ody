package com.ody.eta.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;

@Configuration
public class RouteTimeCallTaskExecutorConfig {

    @Bean("routeTimeCallExecutor")
    public ThreadPoolTaskExecutor spikeThreadPool(MeterRegistry meterRegistry) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int coreCount = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(coreCount * 2);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(50);
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix("route-time-call-task-executor-");
        executor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        executor.getThreadPoolExecutor().prestartAllCoreThreads();

        ExecutorService executorService = executor.getThreadPoolExecutor();
        ExecutorServiceMetrics.monitor(meterRegistry, executorService, "routeTimeCallExecutor", "async");

        return executor;
    }
}
