package com.newestworld.content.messaging;

import com.newestworld.commons.event.ActionDataBatchEvent;
import com.newestworld.commons.event.ActionDataEvent;
import com.newestworld.commons.event.ActionDataRequestBatchEvent;
import com.newestworld.commons.event.ActionDataRequestEvent;
import com.newestworld.content.service.ActionService;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionDataRequestBatchEventConsumer implements Consumer<ActionDataRequestBatchEvent> {

    private final ActionService actionService;
    private final EventPublisher<ActionDataBatchEvent> actionDataBatchEventPublisher;

    @Override
    public void accept(ActionDataRequestBatchEvent event)  {
        log.debug("data request received for {} actions", event.getSize());
        // TODO: 28.01.2023 Нужно получить список экшенов
        actionDataBatchEventPublisher.send(new ActionDataBatchEvent(actions));
    }

}
