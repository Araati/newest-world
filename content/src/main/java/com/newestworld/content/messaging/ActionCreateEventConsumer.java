package com.newestworld.content.messaging;

import com.newestworld.content.service.CompoundActionService;
import com.newestworld.streams.event.ActionCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionCreateEventConsumer implements Consumer<ActionCreateEvent> {

    private final CompoundActionService compoundActionService;

    @Override
    public void accept(ActionCreateEvent event) {
        log.debug("ActionCreate message received with type {}", event.getType());
        //actionService.create(new CompoundActionCreateDTO(event));
    }

}
