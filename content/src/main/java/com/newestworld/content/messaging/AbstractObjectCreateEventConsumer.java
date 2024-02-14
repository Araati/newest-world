package com.newestworld.content.messaging;

import com.newestworld.content.dto.AbstractObjectCreateDTO;
import com.newestworld.content.facade.AbstractObjectFacade;
import com.newestworld.streams.event.AbstractObjectCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class AbstractObjectCreateEventConsumer implements Consumer<AbstractObjectCreateEvent> {

    private final AbstractObjectFacade facade;

    @Override
    public void accept(final AbstractObjectCreateEvent event)  {
        log.debug("AbstractObjectCreate message received for object with name {}", event.getName());
        facade.create(new AbstractObjectCreateDTO(event));
    }
}
