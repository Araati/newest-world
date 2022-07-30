package com.newestworld.scheduler.config;

import com.newestworld.streams.publisher.ActionTimeoutPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        ActionTimeoutPublisher.class
})
@ComponentScan
@EnableAutoConfiguration
@RequiredArgsConstructor
public class BeanConfig {



}
