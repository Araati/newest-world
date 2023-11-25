package com.newestworld.content.service;

import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.CompoundAction;
import com.newestworld.content.dao.ActionParamsRepository;
import com.newestworld.content.dao.BasicActionRepository;
import com.newestworld.content.dao.CompoundActionRepository;
import com.newestworld.content.dto.CompoundActionCreateDTO;
import com.newestworld.content.dto.CompoundActionDTO;
import com.newestworld.content.model.entity.ActionParamsEntity;
import com.newestworld.content.model.entity.BasicActionEntity;
import com.newestworld.content.model.entity.CompoundActionEntity;
import com.newestworld.streams.event.ActionTimeoutCreateEvent;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: 28.01.2023 Возможно стоит разбить на три разных сервиса?
@Slf4j
@Service
@RequiredArgsConstructor
public class CompoundActionService {

    private final CompoundActionRepository compoundActionRepository;
    private final BasicActionRepository basicActionRepository;
    private final ActionParamsRepository actionParamsRepository;

    private final EventPublisher<ActionTimeoutCreateEvent> actionTimeoutCreateEventPublisher;

    // TODO: 30.03.2023 Timeout test value
    private final long timeout = System.currentTimeMillis()+(5*1000);

    public CompoundAction create(final CompoundActionCreateDTO request) {
        CompoundActionEntity compoundActionEntity = new CompoundActionEntity(request);
        compoundActionRepository.save(compoundActionEntity);

        // TODO: 25.11.2023 Extract to ActionParamsService
        List<ActionParameter> input = new ArrayList<>();
        for(int i = 0; i < request.getInput().size(); i++) {
            ActionParamsEntity actionParams = new ActionParamsEntity(compoundActionEntity.getId(), request.getInput().get(i));
            actionParamsRepository.save(actionParams);
            input.add(new ActionParameter(actionParams.getActionId(), actionParams.getName(), actionParams.getValue()));
        }
        ActionParameters actionParameters = new ActionParameters.Impl(input);

        //actionTimeoutCreateEventPublisher.send(new ActionTimeoutCreateEvent(compoundActionEntity.getId(), timeout));
        log.info("CompoundAction with {} id created", compoundActionEntity.getId());
        return new CompoundActionDTO(compoundActionEntity, actionParameters, timeout);

    }

    public void delete(final long id) {
        compoundActionRepository.save(compoundActionRepository.mustFindByIdAndDeletedIsFalse(id).withDeleted(true));

        // TODO: 25.11.2023 Extract to ActionParamsService
        actionParamsRepository.saveAll(actionParamsRepository.findAllByActionIdAndDeletedIsFalse(id).stream()
                .map(x -> x.withDeleted(true)).collect(Collectors.toList()));

        // TODO: 25.11.2023 Extract to BasicActionService
        List<BasicActionEntity> basicActionEntities = basicActionRepository.findAllByActionIdAndDeletedIsFalse(id);
        for (BasicActionEntity basicActionEntity : basicActionEntities) {
            basicActionRepository.save(basicActionEntity.withDeleted(true));
            actionParamsRepository.saveAll(actionParamsRepository.findAllByActionIdAndDeletedIsFalse(basicActionEntity.getId()).stream().map(x -> x.withDeleted(true)).collect(Collectors.toList()));
        }

        log.info("CompoundAction with {} id deleted", id);
    }

    public CompoundAction findById(final long id) {
        CompoundActionEntity entity = compoundActionRepository.mustFindByIdAndDeletedIsFalse(id);

        // TODO: 25.11.2023 Extract to ActionParamsService
        List<ActionParamsEntity> actionParamsEntities = actionParamsRepository.findAllByActionIdAndDeletedIsFalse(id);
        List<ActionParameter> actionParameterList = new ArrayList<>();
        for (ActionParamsEntity source : actionParamsEntities) {
            actionParameterList.add(new ActionParameter(source.getActionId(), source.getName(), source.getValue()));
        }
        ActionParameters actionParameters = new ActionParameters.Impl(actionParameterList);

        return new CompoundActionDTO(entity, actionParameters, timeout);
    }
}
