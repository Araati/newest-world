package com.newestworld.scheduler.messaging;

import com.newestworld.scheduler.dao.ActionTimeoutRepository;
import com.newestworld.streams.event.ActionTimeoutBatchEvent;
import com.newestworld.streams.event.ActionTimeoutEvent;
import com.newestworld.commons.model.IdReference;
import com.newestworld.scheduler.service.ActionTimeoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionTimeoutBatchEventProducer implements Supplier<ActionTimeoutBatchEvent> {

    private final ActionTimeoutService service;

    @Override
    public ActionTimeoutBatchEvent get() {
        final List<IdReference> actionList = service.findAll(System.currentTimeMillis());
        final var eventList = actionList.stream().map(x -> new ActionTimeoutEvent(x.getId())).toList();
        if (eventList.isEmpty()) {
            log.debug("no timeout actions");
            return null;
        }

        log.debug("{} actions has timeout", eventList.size());
        service.markAllProcessing(actionList);
        return new ActionTimeoutBatchEvent(eventList);
    }
}
