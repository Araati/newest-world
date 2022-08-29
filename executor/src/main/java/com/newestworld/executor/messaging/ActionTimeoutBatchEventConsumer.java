package com.newestworld.executor.messaging;

import com.newestworld.commons.event.ActionTimeoutBatchEvent;
import com.newestworld.commons.event.ActionTimeoutEvent;
import com.newestworld.executor.service.ActionExecutorAggregator;
import com.newestworld.executor.service.ActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionTimeoutBatchEventConsumer implements Consumer<ActionTimeoutBatchEvent> {

    private final ActionService actionService;
    private final ActionExecutorAggregator aggregator;

    @Override
    public void accept(final ActionTimeoutBatchEvent event) {
        log.debug("received {} actions for execution", event.getSize());
        var list = actionService.findAllByIds(event.getBatch().stream()
                .map(ActionTimeoutEvent::getId)
                .collect(Collectors.toList()));

        aggregator.execute(list);
    }
}
