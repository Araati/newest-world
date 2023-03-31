package com.newestworld.scheduler.messaging;

import com.newestworld.commons.event.ActionTimeoutCreateEvent;
import com.newestworld.scheduler.dto.ActionTimeoutCreateDTO;
import com.newestworld.scheduler.service.ActionTimeoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionTimeoutCreateEventConsumer implements Consumer<ActionTimeoutCreateEvent> {

    private final ActionTimeoutService service;

    @Override
    public void accept(final ActionTimeoutCreateEvent event)    {
        log.debug("ActionTimeout for action {} created", event.getActionId());
        service.create(new ActionTimeoutCreateDTO(event));
    }

}
