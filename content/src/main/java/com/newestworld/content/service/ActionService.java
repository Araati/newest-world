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

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionService {

    private final StructureParameterService structureParameterService;

    private final ActionRepository actionRepository;
    private final ActionStructureService actionStructureService;

    private final EventPublisher<ActionTimeoutCreateEvent> actionTimeoutCreateEventPublisher;

    // TODO: 30.03.2023 Timeout test value
    private final long timeout = 5*1000;

    public Action create(final ActionCreateDTO request) {

        ActionStructure structure = actionStructureService.findByName(request.getName());
        Map<String, String> parameters = structureParameterService.validateAndInsertDefaultIfRequired(request.getInput(), structure.getParameters());

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
