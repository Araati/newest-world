package com.newestworld.executor.messaging;

import com.newestworld.executor.dto.BasicActionDTO;
import com.newestworld.executor.service.ActionExecutorAggregator;
import com.newestworld.streams.event.CompoundActionDataBatchEvent;
import com.newestworld.streams.event.CompoundActionDataEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionDataBatchEventConsumer implements Consumer<CompoundActionDataBatchEvent> {

    private final ActionExecutorAggregator aggregator;

    //fixme Все экшены, кроме первого, прилетают пустые. Решить СРОЧНО.
    @Override
    public void accept(final CompoundActionDataBatchEvent event) {
        log.debug("Received {} actions for execution", event.getSize());
        for (CompoundActionDataEvent actionDataEvent : event.getBatch())   {
            aggregator.startExecution(actionDataEvent);
        }
    }
}
