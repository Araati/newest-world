package com.newestworld.content.messaging;

import com.newestworld.commons.event.ActionCreateEvent;
import com.newestworld.content.dto.ActionCreateDTO;
import com.newestworld.content.service.ActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionCreateEventConsumer implements Consumer<ActionCreateEvent> {

    private final ActionService actionService;

    @Override
    public void accept(ActionCreateEvent event) {
        log.debug("ActionDelete message received with type {}", event.getType());
        actionService.create(new ActionCreateDTO(event));
    }

}
