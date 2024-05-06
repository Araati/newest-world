package com.newestworld.executor.messaging;

import com.newestworld.executor.service.ActionExecutorAggregator;
import com.newestworld.streams.event.batch.ActionDataBatchEvent;
import com.newestworld.streams.event.ActionDataEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionDataBatchEventConsumer implements Consumer<ActionDataBatchEvent> {

    private final ActionExecutorAggregator aggregator;

    @Override
    public void accept(final ActionDataBatchEvent event) {
        log.debug("Received {} actions for execution", event.getSize());
        for (ActionDataEvent actionDataEvent : event.getBatch())   {
            aggregator.startExecution(actionDataEvent);
        }
    }
}
