package com.newestworld.content.messaging;

import com.newestworld.content.service.CompoundActionService;
import com.newestworld.streams.event.ActionDataBatchEvent;
import com.newestworld.streams.event.ActionDataEvent;
import com.newestworld.streams.event.ActionDataRequestBatchEvent;
import com.newestworld.streams.event.ActionDataRequestEvent;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionDataRequestBatchEventConsumer implements Consumer<ActionDataRequestBatchEvent> {

    private final CompoundActionService compoundActionService;
    private final EventPublisher<ActionDataBatchEvent> actionDataBatchEventPublisher;

    @Override
    public void accept(ActionDataRequestBatchEvent event)  {
        log.debug("data request received for {} actions", event.getSize());
        List<ActionDataRequestEvent> requests = new ArrayList<>(event.getBatch());
        List<ActionDataEvent> dataEvents = new ArrayList<>();

        for (ActionDataRequestEvent request : requests) {
            //BasicAction basicAction = actionService.findById(request.getId());
            //dataEvents.add(new ActionDataEvent(basicAction.getId(), basicAction.getType(), basicAction.getParameters().getAll(), basicAction.getCreatedAt()));
        }

        actionDataBatchEventPublisher.send(new ActionDataBatchEvent(dataEvents));
    }

}
