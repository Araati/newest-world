package com.newestworld.content.service;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.commons.model.*;
import com.newestworld.content.dao.ActionRepository;
import com.newestworld.content.dto.*;
import com.newestworld.content.model.entity.ActionEntity;
import com.newestworld.streams.event.ActionTimeoutCreateEvent;
import com.newestworld.streams.publisher.EventPublisher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        ActionStructure structure = actionStructureService.findByName(request.getName());

        // Check, if all inputs are present
        Set<String> input = request.getInput().keySet();
        ModelParameters expectedParameters = structure.getParameters();
        for (ModelParameter parameter : expectedParameters.getAll())    {
            if(!input.contains(parameter.getName()) && parameter.isRequired())   {
                throw new ValidationFailedException("Input parameter not present : " + parameter.getName());
            }
        }

        // Saving action
        ActionEntity actionEntity = new ActionEntity(request, structure.getId());
        actionRepository.save(actionEntity);

        // Saving action parameters
        //fixme insert values from createDTO into modelParams from structure and save them
        List<ModelParameterCreateDTO> createDTOS = new ArrayList<>();
        for (Map.Entry<String, String> pair: request.getInput().entrySet()) {
            ModelParameter structureParameter = structure.getParameters().mustGetByName(pair.getKey());
            ModelParameterCreateDTO dto = new ModelParameterCreateDTO(
                    pair.getKey(),
                    structureParameter.isRequired(),
                    pair.getValue(),
                    structureParameter.getType(),
                    structureParameter.getInit(),
                    structureParameter.getMin(),
                    structureParameter.getMax());
            createDTOS.add(dto);
        }
        ModelParameters parameters = modelParameterService.create(actionEntity.getId(), createDTOS);
        actionTimeoutCreateEventPublisher.send(new ActionTimeoutCreateEvent(actionEntity.getId(), timeout));

        log.info("Action with {} id created", actionEntity.getId());
        return new ActionDTO(actionEntity, parameters, timeout);

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
