package com.ody.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.AbstractProtocol;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TomcatThreadPoolMonitor implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    private volatile Connector connector;

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(connector -> this.connector = connector);
    }

    @Scheduled(fixedRate = 100) // 1초마다 실행
    public void logThreadPoolStatus() {
        if (connector == null) return;
        ProtocolHandler handler = connector.getProtocolHandler();

        if (handler instanceof AbstractProtocol<?> protocol) {
            var executor = (ThreadPoolExecutor) protocol.getExecutor(); // Tomcat 전용 타입
            log.info("[TomcatThreadPool] Active={}, PoolSize={}, CorePool={}, MaxPool={}, CompletedTasks={}",
                    executor.getActiveCount(),
                    executor.getPoolSize(),
                    executor.getCorePoolSize(),
                    executor.getMaximumPoolSize(),
                    executor.getCompletedTaskCount());
        }
    }
}

