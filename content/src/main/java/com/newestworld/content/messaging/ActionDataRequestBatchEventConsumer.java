package com.newestworld.content.messaging;

import com.newestworld.commons.model.*;
import com.newestworld.content.facade.ActionFacade;
import com.newestworld.content.facade.ActionStructureFacade;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionDataRequestBatchEventConsumer implements Consumer<ActionDataRequestBatchEvent> {

    private final NodeService nodeService;
    private final ActionFacade actionFacade;
    private final ActionStructureFacade actionStructureFacade;
    private final EventPublisher<ActionDataBatchEvent> actionDataBatchEventPublisher;

    @Override
    public void accept(final ActionDataRequestBatchEvent event)  {
        log.debug("Data request received for {} actions", event.getSize());
        List<ActionDataRequestEvent> requests = new ArrayList<>(event.getBatch());
        List<ActionDataEvent> dataEvents = new ArrayList<>();

        for (final ActionDataRequestEvent request : requests) {
            final Action action = actionFacade.findById(request.getId());
            final ActionStructure structure = actionStructureFacade.findById(action.getStructureId());
            final List<NodeEvent> nodes = nodeService.findAllById(structure.getId())
                    .stream().map(NodeEvent::new).toList();

            List<ModelParameter> parameters = new ArrayList<>();
            for (final ModelParameter modelParameter : structure.getParameters()) {
                parameters.add(new ModelParameter(
                        modelParameter.getName(),
                        modelParameter.isRequired(),
                        action.getParameters().get(modelParameter.getName()),
                        modelParameter.getType(),
                        modelParameter.getMax(),
                        modelParameter.getMin()
                ));
            }
            dataEvents.add(new ActionDataEvent(request.getId(), new ModelParameters.Impl(parameters), nodes));
        }

        actionDataBatchEventPublisher.send(new ActionDataBatchEvent(dataEvents));
    }

}
