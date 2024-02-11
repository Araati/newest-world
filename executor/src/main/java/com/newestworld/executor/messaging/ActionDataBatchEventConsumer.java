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

    @Override
    public void accept(final CompoundActionDataBatchEvent event) {
        log.debug("received {} actions for execution", event.getSize());
        for (CompoundActionDataEvent actionDataEvent : event.getBatch())   {
            System.out.println(actionDataEvent.getBasicActions().size());
            aggregator.execute(actionDataEvent.getInput(), actionDataEvent.getBasicActions()
                    .stream().map(BasicActionDTO::new).collect(Collectors.toList()));
        }
    }
}
