package com.newestworld.streams.publisher;

import com.newestworld.commons.event.ActionDataBatchEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;

@Slf4j
@RequiredArgsConstructor
public class ActionDataBatchEventPublisher implements EventPublisher<ActionDataBatchEvent> {

    private final StreamBridge publisher;
    private final String topic;

    @Override
    public void send(final ActionDataBatchEvent event) {
        log.debug("Send event {} to {} topic", event.getClass(), topic);
        publisher.send(topic, event);
    }

}
