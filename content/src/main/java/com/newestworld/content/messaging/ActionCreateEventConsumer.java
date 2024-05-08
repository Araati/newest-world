package com.newestworld.content.messaging;

import com.newestworld.content.dto.ActionCreateDTO;
import com.newestworld.content.facade.ActionFacade;
import com.newestworld.streams.event.ActionCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionCreateEventConsumer implements Consumer<ActionCreateEvent> {

    private final ActionFacade facade;

    @Override
    public void accept(final ActionCreateEvent event) {
        log.debug("ActionCreate message received with name {}", event.getName());
        facade.create(new ActionCreateDTO(event.getName(), event.getInput()));
    }

}
