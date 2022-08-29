package com.newestworld.content.messaging;

import com.newestworld.commons.event.ActionDeleteEvent;
import com.newestworld.content.service.ActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionDeleteEventConsumer implements Consumer<ActionDeleteEvent> {

    private final ActionService actionService;

    @Override
    public void accept(final ActionDeleteEvent event)  {
        log.debug("ActionDelete message received for action {}", event.getActionId());
        actionService.delete(event.getActionId());
    }

}
