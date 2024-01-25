package com.newestworld.content.config;

import com.newestworld.streams.event.CompoundActionDataBatchEvent;
import com.newestworld.streams.event.ActionTimeoutCreateEvent;
import com.newestworld.streams.publisher.AbstractEventPublisher;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//todo: take topic name from application.yml
@Configuration
@RequiredArgsConstructor
public class PublisherConfig {

    private final StreamBridge bridge;

    @Bean
    public EventPublisher<CompoundActionDataBatchEvent> actionDataBatchEventPublisher()   {
        return new AbstractEventPublisher<>(bridge, "actionDataBatchEventProducer-out-0");
    }

    @Bean
    public EventPublisher<ActionTimeoutCreateEvent> actionTimeoutCreateEventPublisher()    {
        return new AbstractEventPublisher<>(bridge, "actionTimeoutCreateEventProducer-out-0");
    }
}
