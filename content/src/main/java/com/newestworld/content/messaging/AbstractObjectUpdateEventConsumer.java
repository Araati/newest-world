package com.newestworld.content.messaging;

import com.newestworld.content.dto.AbstractObjectUpdateDTO;
import com.newestworld.content.service.AbstractObjectService;
import com.newestworld.streams.event.AbstractObjectUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class AbstractObjectUpdateEventConsumer implements Consumer<AbstractObjectUpdateEvent> {

    private final AbstractObjectService service;

    @Override
    public void accept(final AbstractObjectUpdateEvent event)  {
        log.debug("AbstractObjectUpdate message received for object {}", event.getId());
        service.update(new AbstractObjectUpdateDTO(event));
    }
}
