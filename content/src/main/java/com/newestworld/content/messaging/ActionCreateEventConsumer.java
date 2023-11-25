package com.newestworld.content.messaging;

import com.newestworld.content.dto.CompoundActionCreateDTO;
import com.newestworld.content.service.CompoundActionService;
import com.newestworld.streams.event.CompoundActionCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionCreateEventConsumer implements Consumer<CompoundActionCreateEvent> {

    private final CompoundActionService compoundActionService;

    @Override
    public void accept(final CompoundActionCreateEvent event) {
        log.debug("CompoundActionCreate message received with name {}", event.getName());
        compoundActionService.create(new CompoundActionCreateDTO(event));
    }

}
