package com.newestworld.executor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        com.newestworld.streams.publisher.FactoryUpdatePublisher.class,
        com.newestworld.streams.publisher.ActionDeletePublisher.class,
        com.newestworld.streams.publisher.ActionCreatePublisher.class
})
@ComponentScan
@EnableAutoConfiguration
@RequiredArgsConstructor
public class BeanConfig {

}
