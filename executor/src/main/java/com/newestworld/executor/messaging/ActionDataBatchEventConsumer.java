package com.newestworld.executor.messaging;

import com.newestworld.executor.service.ActionExecutorAggregator;
import com.newestworld.streams.event.CompoundActionDataBatchEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionDataBatchEventConsumer implements Consumer<CompoundActionDataBatchEvent> {

    private final ActionExecutorAggregator aggregator;

    @Override
    public void accept(final CompoundActionDataBatchEvent event) {
        log.debug("received {} actions for execution", event.getSize());
        // fixme restore action execution
        /*for (CompoundActionDataEvent actionDataEvent : event.getBatch())   {
            aggregator.execute(actionDataEvent.getInput(), actionDataEvent.getBasicActions());
        }*/
    }
}
