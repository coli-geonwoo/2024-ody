package com.ody.eta.config;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class RouteTimeCallTaskExecutorConfig {

    @Bean("routeTimeCallExecutor")
    public ThreadPoolTaskExecutor spikeThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int coreCount = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(coreCount * 2); // CPU코어 * 2 -> I/O 작업이 주가 되므로
        executor.setMaxPoolSize(100);       // 최대 100개의 동시처리를 대응
        executor.setQueueCapacity(50);      // 순간 버퍼
        executor.setKeepAliveSeconds(30);   //
        executor.setThreadNamePrefix("route-time-call-task-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
