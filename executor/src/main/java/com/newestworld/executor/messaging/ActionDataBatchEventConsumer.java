package com.newestworld.executor.messaging;

import com.newestworld.commons.event.ActionDataBatchEvent;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.executor.dto.ActionDTO;
import com.newestworld.executor.service.ActionExecutorAggregator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionDataBatchEventConsumer implements Consumer<ActionDataBatchEvent> {

    private final ActionExecutorAggregator aggregator;

    @Override
    public void accept(final ActionDataBatchEvent event) {
        log.debug("received {} actions for execution", event.getSize());
        aggregator.execute(event.getBatch().stream()
               .map(x -> new ActionDTO(x.getId(), x.getType(), new ActionParameters.Impl(x.getParameters()), x.getCreatedAt()))
               .collect(Collectors.toList()));
    }
}
