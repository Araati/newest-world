package com.newestworld.executor.messaging;

import com.newestworld.streams.event.batch.ActionDataRequestBatchEvent;
import com.newestworld.streams.event.batch.ActionTimeoutBatchEvent;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionTimeoutBatchEventConsumer implements Consumer<ActionTimeoutBatchEvent> {
    private final EventPublisher<ActionDataRequestBatchEvent> actionDataRequestBatchEventPublisher;

    @Override
    public void accept(final ActionTimeoutBatchEvent event) {
        log.debug("Send data request for {} actions", event.getSize());
        actionDataRequestBatchEventPublisher.send(new ActionDataRequestBatchEvent(event));
    }
}
