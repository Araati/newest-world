package com.newestworld.scheduler.messaging;

import com.newestworld.commons.event.ActionDeleteEvent;
import com.newestworld.scheduler.service.ActionTimeoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionDeleteEventConsumer implements Consumer<ActionDeleteEvent> {

    private final ActionTimeoutService service;

    @Override
    public void accept(final ActionDeleteEvent event) {
        log.debug("Delete action timeout {}", event.getActionId());
        service.delete(event.getActionId());
    }
}
