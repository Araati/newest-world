package com.newestworld.executor.config;

import com.newestworld.streams.event.*;
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
    public EventPublisher<ActionDeleteEvent> actionDeleteEventPublisher() {
        return new AbstractEventPublisher<>(bridge, "actionDeleteEventProducer-out-0");
    }

    @Bean
    public EventPublisher<AbstractObjectCreateEvent> abstractObjectCreateEventPublisher()   {
        return new AbstractEventPublisher<>(bridge, "abstractObjectCreateEventProducer-out-0");
    }

    @Bean
    public EventPublisher<AbstractObjectUpdateEvent> abstractObjectUpdateEventPublisher() {
        return new AbstractEventPublisher<>(bridge, "abstractObjectUpdateEventProducer-out-0");
    }

    @Bean
    public EventPublisher<CompoundActionCreateEvent> actionCreateEventPublisher()   {
        return new AbstractEventPublisher<>(bridge, "actionCreateEventProducer-out-0");
    }

    @Bean
    public EventPublisher<ActionDataRequestBatchEvent> actionDataRequestBatchEventPublisher()   {
        return new AbstractEventPublisher<>(bridge, "actionDataRequestBatchEventProducer-out-0");
    }
}
