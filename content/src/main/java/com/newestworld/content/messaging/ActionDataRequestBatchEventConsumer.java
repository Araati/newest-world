package com.newestworld.content.messaging;

import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.content.service.ActionParamsService;
import com.newestworld.content.service.BasicActionService;
import com.newestworld.streams.event.CompoundActionDataBatchEvent;
import com.newestworld.streams.event.CompoundActionDataEvent;
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

    private final BasicActionService basicActionService;
    private final ActionParamsService actionParamsService;
    private final EventPublisher<CompoundActionDataBatchEvent> actionDataBatchEventPublisher;

    @Override
    public void accept(final ActionDataRequestBatchEvent event)  {
        log.debug("data request received for {} actions", event.getSize());
        List<ActionDataRequestEvent> requests = new ArrayList<>(event.getBatch());
        List<CompoundActionDataEvent> dataEvents = new ArrayList<>();

        for (ActionDataRequestEvent request : requests) {
            List<BasicAction> basicActions = basicActionService.findAllById(request.getId());
            ActionParameters input = actionParamsService.findById(request.getId());
            dataEvents.add(new CompoundActionDataEvent(input, basicActions));
        }

        actionDataBatchEventPublisher.send(new CompoundActionDataBatchEvent(dataEvents));
    }

}
