package com.newestworld.content.service;

import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.CompoundAction;
import com.newestworld.content.dao.CompoundActionRepository;
import com.newestworld.content.dto.CompoundActionCreateDTO;
import com.newestworld.content.dto.CompoundActionDTO;
import com.newestworld.content.model.entity.CompoundActionEntity;
import com.newestworld.streams.event.ActionTimeoutCreateEvent;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompoundActionService {

    private final ActionParamsService actionParamsService;
    private final BasicActionService basicActionService;

    private final CompoundActionRepository compoundActionRepository;

    // FIXME: 25.11.2023 Restore messaging
    private final EventPublisher<ActionTimeoutCreateEvent> actionTimeoutCreateEventPublisher;

    // TODO: 30.03.2023 Timeout test value
    private final long timeout = System.currentTimeMillis()+(5*1000);

    public CompoundAction create(final CompoundActionCreateDTO request) {
        CompoundActionEntity compoundActionEntity = new CompoundActionEntity(request);
        compoundActionRepository.save(compoundActionEntity);
        ActionParameters actionParameters = actionParamsService.create(compoundActionEntity.getId(), request.getInput());
        actionTimeoutCreateEventPublisher.send(new ActionTimeoutCreateEvent(compoundActionEntity.getId(), timeout));
        log.info("CompoundAction with {} id created", compoundActionEntity.getId());
        return new CompoundActionDTO(compoundActionEntity, actionParameters, timeout);

    }

    public void delete(final long id) {
        compoundActionRepository.save(compoundActionRepository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));
        actionParamsService.delete(id);
        basicActionService.deleteAll(id);
        log.info("CompoundAction with {} id deleted", id);
    }

    public CompoundAction findById(final long id) {
        CompoundActionEntity entity = compoundActionRepository.mustFindByIdAndDeletedIsFalse(id);
        ActionParameters actionParameters = actionParamsService.findById(id);
        return new CompoundActionDTO(entity, actionParameters, timeout);
    }
}
