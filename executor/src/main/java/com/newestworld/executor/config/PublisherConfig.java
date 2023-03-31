package com.newestworld.executor.config;

import com.newestworld.commons.event.ActionCreateEvent;
import com.newestworld.commons.event.ActionDataRequestBatchEvent;
import com.newestworld.commons.event.ActionDeleteEvent;
import com.newestworld.commons.event.FactoryUpdateEvent;
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
    public EventPublisher<FactoryUpdateEvent> factoryUpdateEventPublisher() {
        return new AbstractEventPublisher<>(bridge, "factoryUpdateEventProducer-out-0");
    }

    @Bean
    public EventPublisher<ActionCreateEvent> actionCreateEventPublisher()   {
        return new AbstractEventPublisher<>(bridge, "actionCreateEventProducer-out-0");
    }

    @Bean
    public EventPublisher<ActionDataRequestBatchEvent> actionDataRequestBatchEventPublisher()   {
        return new AbstractEventPublisher<>(bridge, "actionDataRequestBatchEventProducer-out-0");
    }
}
