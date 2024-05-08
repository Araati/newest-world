package com.newestworld.content.service;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.commons.model.*;
import com.newestworld.content.dao.ActionRepository;
import com.newestworld.content.dto.*;
import com.newestworld.content.model.entity.ActionEntity;
import com.newestworld.streams.event.ActionTimeoutCreateEvent;
import com.newestworld.streams.publisher.EventPublisher;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionService {

    private final Validator validator;
    private final StructureParameterService structureParameterService;

    private final ActionRepository actionRepository;
    private final ActionStructureService actionStructureService;

    private final EventPublisher<ActionTimeoutCreateEvent> actionTimeoutCreateEventPublisher;

    // TODO: 30.03.2023 Timeout test value
    private final long timeout = 5*1000;

    public Action create(final ActionCreateDTO request) {

        ActionStructure structure = actionStructureService.findByName(request.getName());

        // Check, if all inputs are present
        Set<String> input = request.getInput().keySet();
        List<StructureParameter> expectedParameters = structure.getParameters();
        for (StructureParameter parameter : expectedParameters)    {
            if(!input.contains(parameter.getName()) && parameter.isRequired() && parameter.getInit() == null)   {
                throw new ValidationFailedException("Input parameter not present : " + parameter.getName());
            }
        }

        // Check, if input is valid and insert init values if needed
        //fixme code duplicate
        Map<String, String> inputMap = request.getInput();
        Map<String, String> parameters = new HashMap<>();
        for (StructureParameter parameter : expectedParameters) {
            var validatableBuilder = ModelParameter.builder();
            Set<ConstraintViolation<ModelParameter>> violations;
            validatableBuilder
                    .name(parameter.getName())
                    .required(parameter.isRequired())
                    .type(parameter.getType())
                    .min(parameter.getMin())
                    .max(parameter.getMax());
            if (inputMap.containsKey(parameter.getName())) {
                validatableBuilder.data(inputMap.get(parameter.getName()));
            } else if (parameter.getInit() != null) {
                validatableBuilder.data(parameter.getInit());
            } else if (parameter.isRequired()) {
                throw new ValidationFailedException("Input parameter not present : " + parameter.getName());
            }
            var validatable = validatableBuilder.build();
            violations = validator.validate(validatableBuilder.build());
            if (!violations.isEmpty()) {
                throw new ValidationFailedException();
            }
            parameters.put(parameter.getName(), validatable.getData());
        }


        // Saving action
        ActionEntity actionEntity = new ActionEntity(request, parameters, structure.getId());
        actionRepository.save(actionEntity);

        actionTimeoutCreateEventPublisher.send(new ActionTimeoutCreateEvent(actionEntity.getId(), timeout));

        log.info("Action with {} id created", actionEntity.getId());
        return new ActionDTO(actionEntity, timeout);

    }

    public void delete(final long id) {
        actionRepository.save(actionRepository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));
        structureParameterService.delete(id);
        log.info("Action with {} id deleted", id);
    }

    public Action findById(final long id) {
        ActionEntity entity = actionRepository.mustFindByIdAndDeletedIsFalse(id);
        return new ActionDTO(entity, timeout);
    }
}
