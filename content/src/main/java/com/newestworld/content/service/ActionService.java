package com.newestworld.content.service;

import com.newestworld.commons.model.*;
import com.newestworld.content.dao.ActionRepository;
import com.newestworld.content.dto.*;
import com.newestworld.content.model.entity.ActionEntity;
import com.newestworld.streams.event.ActionTimeoutCreateEvent;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionService {

    private final ModelParameterService modelParameterService;
    private final NodeService nodeService;

    private final ActionRepository actionRepository;
    private final ActionStructureService actionStructureService;

    private final EventPublisher<ActionTimeoutCreateEvent> actionTimeoutCreateEventPublisher;

    // TODO: 30.03.2023 Timeout test value
    private final long timeout = 5*1000;

    public Action create(final ActionCreateDTO request) {

        // Validation by structure
        ActionStructure structure = actionStructureService.findByName(request.getName());
        //fixme validation
        /*
        List<String> input = request.getInput().stream().map(ModelParameterCreateDTO::getName).collect(Collectors.toList());
        List<StructureProperty> expectedInputs = structure.getProperties();
        if (!new HashSet<>(input).containsAll(expectedInputs.stream().map(StructureProperty::getName).toList())) {
            throw new ValidationFailedException();
        }*/

        // Saving action
        ActionEntity actionEntity = new ActionEntity(request, structure.getId());
        actionRepository.save(actionEntity);

        // Saving action parameters
        //fixme insert values from createDTO into modelParams from structure and save them
        ModelParameters modelParameters = null;
        //ModelParameters modelParameters = modelParameterService.create(actionEntity.getId(), request.getInput());
        actionTimeoutCreateEventPublisher.send(new ActionTimeoutCreateEvent(actionEntity.getId(), timeout));

        log.info("Action with {} id created", actionEntity.getId());
        return new ActionDTO(actionEntity, modelParameters, timeout);

    }

    public void delete(final long id) {
        actionRepository.save(actionRepository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));
        modelParameterService.delete(id);
        log.info("Action with {} id deleted", id);
    }

    public Action findById(final long id) {
        ActionEntity entity = actionRepository.mustFindByIdAndDeletedIsFalse(id);
        ModelParameters modelParameters = modelParameterService.findById(id);
        return new ActionDTO(entity, modelParameters, timeout);
    }
}
