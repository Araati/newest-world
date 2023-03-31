package com.newestworld.streams.publisher;

import com.newestworld.streams.event.ActionDeleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;

@Slf4j
@RequiredArgsConstructor
public class ActionDeletedEventPublisher implements EventPublisher<ActionDeleteEvent> {

    private final StreamBridge publisher;

    private final String topic;

    @Override
    public void send(final ActionDeleteEvent event) {
        log.debug("Send event {} to {} topic", event.getClass().getName(), topic);
        publisher.send(topic, event);
    }
}
