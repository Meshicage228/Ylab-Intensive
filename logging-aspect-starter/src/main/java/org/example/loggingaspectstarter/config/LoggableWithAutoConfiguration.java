package org.example.loggingaspectstarter.config;

import org.example.loggingaspectstarter.aop.aspect.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LoggableWithAutoConfiguration {
    public LoggableWithAutoConfiguration() {
    }

    @Bean
    public LoggingAspect loggingAspect() {
       return new LoggingAspect();
    }
}
