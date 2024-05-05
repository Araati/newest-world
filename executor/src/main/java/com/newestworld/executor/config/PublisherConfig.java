package com.newestworld.executor.config;

import com.newestworld.streams.event.*;
import com.newestworld.streams.event.batch.ActionDataRequestBatchEvent;
import com.newestworld.streams.publisher.*;
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
        return new ActionDeletedEventPublisher(bridge, "actionDeleteEventProducer-out-0");
    }

    @Bean
    public EventPublisher<AbstractObjectCreateEvent> abstractObjectCreateEventPublisher()   {
        return new AbstractObjectCreateEventPublisher(bridge, "abstractObjectCreateEventProducer-out-0");
    }

    @Bean
    public EventPublisher<AbstractObjectUpdateEvent> abstractObjectUpdateEventPublisher() {
        return new AbstractObjectUpdateEventPublisher(bridge, "abstractObjectUpdateEventProducer-out-0");
    }

    @Bean
    public EventPublisher<CompoundActionCreateEvent> actionCreateEventPublisher()   {
        return new ActionCreateEventPublisher(bridge, "actionCreateEventProducer-out-0");
    }

    @Bean
    public EventPublisher<ActionDataRequestBatchEvent> actionDataRequestBatchEventPublisher()   {
        return new ActionDataRequestBatchEventPublisher(bridge, "actionDataRequestBatchEventProducer-out-0");
    }
}
