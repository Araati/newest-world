package com.newestworld.content.config;

import com.newestworld.streams.event.batch.ActionDataBatchEvent;
import com.newestworld.streams.event.ActionTimeoutCreateEvent;
import com.newestworld.streams.publisher.ActionDataBatchEventPublisher;
import com.newestworld.streams.publisher.ActionTimeoutCreateEventPublisher;
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
    public EventPublisher<ActionDataBatchEvent> actionDataBatchEventPublisher()   {
        return new ActionDataBatchEventPublisher(bridge, "actionDataBatchEventProducer-out-0");
    }

    @Bean
    public EventPublisher<ActionTimeoutCreateEvent> actionTimeoutCreateEventPublisher()    {
        return new ActionTimeoutCreateEventPublisher(bridge, "actionTimeoutCreateEventProducer-out-0");
    }
}
