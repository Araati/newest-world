package com.newestworld.content.messaging;

import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.commons.model.CompoundActionStructure;
import com.newestworld.content.service.ActionParamsService;
import com.newestworld.content.service.BasicActionService;
import com.newestworld.content.service.CompoundActionService;
import com.newestworld.content.service.CompoundActionStructureService;
import com.newestworld.streams.event.*;
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

    private final BasicActionService basicActionService;
    private final ActionParamsService actionParamsService;
    private final CompoundActionService compoundActionService;
    private final CompoundActionStructureService compoundActionStructureService;
    private final EventPublisher<CompoundActionDataBatchEvent> actionDataBatchEventPublisher;

    @Override
    public void accept(final ActionDataRequestBatchEvent event)  {
        log.debug("data request received for {} actions", event.getSize());
        List<ActionDataRequestEvent> requests = new ArrayList<>(event.getBatch());
        List<CompoundActionDataEvent> dataEvents = new ArrayList<>();

        for (ActionDataRequestEvent request : requests) {
            CompoundActionStructure structure = compoundActionStructureService.findById(compoundActionService.findById(request.getId()).getStructureId());
            List<BasicActionEvent> basicActions = basicActionService.findAllById(structure.getId())
                    .stream().map(BasicActionEvent::new).collect(Collectors.toList());
            ActionParameters input = actionParamsService.findById(request.getId());
            dataEvents.add(new CompoundActionDataEvent(request.getId(), input, basicActions));
        }

        actionDataBatchEventPublisher.send(new CompoundActionDataBatchEvent(dataEvents));
    }

}
