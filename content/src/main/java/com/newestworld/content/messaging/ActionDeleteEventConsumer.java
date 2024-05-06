package com.newestworld.content.messaging;

import com.newestworld.content.facade.ActionFacade;
import com.newestworld.streams.event.ActionDeleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionDeleteEventConsumer implements Consumer<ActionDeleteEvent> {

    private final ActionFacade facade;

    @Override
    public void accept(final ActionDeleteEvent event)  {
        log.debug("ActionDelete message received for action {}", event.getActionId());
        facade.delete(event.getActionId());
    }

}
