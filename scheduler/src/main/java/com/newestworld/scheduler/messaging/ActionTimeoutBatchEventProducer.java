package com.newestworld.scheduler.messaging;

import com.newestworld.commons.event.ActionTimeoutBatchEvent;
import com.newestworld.commons.event.ActionTimeoutEvent;
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
        //todo: need to mark an action timeout like "sended" or "in-progress" to prevent duplicated event
        final List<IdReference> actionList = service.findAll(System.currentTimeMillis());
        final var eventList = actionList.stream().map(x -> new ActionTimeoutEvent(x.getId())).collect(Collectors.toList());
        if (eventList.isEmpty()) {
            log.debug("no timeout actions");
            return null;
        }

        log.debug("{} actions has timeout", eventList.size());

        return new ActionTimeoutBatchEvent(eventList);
    }
}
