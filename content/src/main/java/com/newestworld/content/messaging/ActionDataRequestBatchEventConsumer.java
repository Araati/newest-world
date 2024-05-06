package com.newestworld.content.messaging;

import com.newestworld.commons.model.ModelParameters;
import com.newestworld.commons.model.ActionStructure;
import com.newestworld.content.facade.ActionFacade;
import com.newestworld.content.facade.ActionStructureFacade;
import com.newestworld.content.service.ModelParameterService;
import com.newestworld.content.service.NodeService;
import com.newestworld.streams.event.*;
import com.newestworld.streams.event.batch.ActionDataRequestBatchEvent;
import com.newestworld.streams.event.batch.ActionDataBatchEvent;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionDataRequestBatchEventConsumer implements Consumer<ActionDataRequestBatchEvent> {

    private final NodeService nodeService;
    private final ModelParameterService modelParameterService;
    private final ActionFacade actionFacade;
    private final ActionStructureFacade actionStructureFacade;
    private final EventPublisher<ActionDataBatchEvent> actionDataBatchEventPublisher;

    @Override
    public void accept(final ActionDataRequestBatchEvent event)  {
        log.debug("Data request received for {} actions", event.getSize());
        List<ActionDataRequestEvent> requests = new ArrayList<>(event.getBatch());
        List<ActionDataEvent> dataEvents = new ArrayList<>();

        for (ActionDataRequestEvent request : requests) {
            ActionStructure structure = actionStructureFacade.findById(actionFacade.findById(request.getId()).getStructureId());
            List<NodeEvent> nodes = nodeService.findAllById(structure.getId())
                    .stream().map(NodeEvent::new).collect(Collectors.toList());
            ModelParameters input = modelParameterService.findById(request.getId());
            dataEvents.add(new ActionDataEvent(request.getId(), input, nodes));
        }

        actionDataBatchEventPublisher.send(new ActionDataBatchEvent(dataEvents));
    }

}
