package com.newestworld.streams.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventPublisher<T> implements EventPublisher<T> {

    private final StreamBridge publisher;
    private final String topic;

    @Override
    public void send(final T event) {
        log.debug("Send event {} to {} topic", event.getClass().getName(), topic);
        publisher.send(topic, event);
    }
}
