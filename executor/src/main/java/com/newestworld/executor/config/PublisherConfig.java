package com.newestworld.executor.config;

import com.newestworld.commons.event.ActionDeleteEvent;
import com.newestworld.commons.event.FactoryUpdateEvent;
import com.newestworld.streams.publisher.ActionDeletedEventPublisher;
import com.newestworld.streams.publisher.EventPublisher;
import com.newestworld.streams.publisher.FactoryUpdateEventPublisher;
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
    public EventPublisher<ActionDeleteEvent> actionDeleteEventEventPublisher() {
        return new ActionDeletedEventPublisher(bridge, "actionDeletedEventProducer-out-0");
    }

    @Bean
    public EventPublisher<FactoryUpdateEvent> factoryUpdateEventPublisher() {
        return new FactoryUpdateEventPublisher(bridge, "factoryUpdateEventProducer-out-0");
    }
}
