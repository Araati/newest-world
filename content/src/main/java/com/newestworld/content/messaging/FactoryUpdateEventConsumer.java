package com.newestworld.content.messaging;

import com.newestworld.streams.event.FactoryUpdateEvent;
import com.newestworld.content.dto.FactoryUpdateDTO;
import com.newestworld.content.service.FactoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class FactoryUpdateEventConsumer implements Consumer<FactoryUpdateEvent> {

    private final FactoryService factoryService;

    @Override
    public void accept(final FactoryUpdateEvent event)  {
        log.debug("FactoryUpdate message received for factory {}", event.getFactoryId());
        factoryService.update(new FactoryUpdateDTO(event), event.getFactoryId());
    }

}
