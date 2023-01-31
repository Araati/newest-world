package com.newestworld.content.messaging;

import com.newestworld.commons.event.ActionDataBatchEvent;
import com.newestworld.commons.event.ActionDataEvent;
import com.newestworld.commons.event.ActionDataRequestBatchEvent;
import com.newestworld.commons.event.ActionDataRequestEvent;
import com.newestworld.commons.model.Action;
import com.newestworld.content.service.ActionService;
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

    private final ActionService actionService;
    private final EventPublisher<ActionDataBatchEvent> actionDataBatchEventPublisher;

    @Override
    public void accept(ActionDataRequestBatchEvent event)  {
        log.debug("data request received for {} actions", event.getSize());
        List<ActionDataRequestEvent> requests = new ArrayList<>(event.getBatch());
        List<ActionDataEvent> dataEvents = new ArrayList<>();

        for (ActionDataRequestEvent request : requests) {
            Action action = actionService.findById(request.getId());
            dataEvents.add(new ActionDataEvent(action.getId(), action.getType(), action.getParameters().getAll(), action.getCreatedAt()));
        }

        actionDataBatchEventPublisher.send(new ActionDataBatchEvent(dataEvents));
    }

}
