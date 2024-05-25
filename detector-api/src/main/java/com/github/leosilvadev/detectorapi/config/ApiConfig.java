package com.github.leosilvadev.detectorapi.config;

import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    public JettyServletWebServerFactory jettyServletWebServerFactory() {
        final var factory = new JettyServletWebServerFactory();
        factory.addServerCustomizers(server -> {
            final var gzipHandler = new GzipHandler();
            gzipHandler.setInflateBufferSize(1);
            gzipHandler.setHandler(server.getHandler());
            server.setHandler(gzipHandler);
        });
        return factory;
    }
}
