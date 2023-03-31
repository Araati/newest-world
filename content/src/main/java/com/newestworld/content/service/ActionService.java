package com.newestworld.content.service;

import com.newestworld.streams.event.ActionTimeoutCreateEvent;
import com.newestworld.commons.model.Action;
import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.content.dao.ActionParamsRepository;
import com.newestworld.content.dao.ActionRepository;
import com.newestworld.content.dto.ActionCreateDTO;
import com.newestworld.content.dto.ActionDTO;
import com.newestworld.content.model.entity.ActionEntity;
import com.newestworld.content.model.entity.ActionParamsEntity;
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
public class ActionService {

    private final ActionRepository actionRepository;
    private final ActionParamsRepository actionParamsRepository;

    private final EventPublisher<ActionTimeoutCreateEvent> actionTimeoutCreateEventPublisher;

    // TODO: 30.03.2023 Timeout test value
    private final long timeout = System.currentTimeMillis()+(5*1000);

    public Action create(final ActionCreateDTO request) {
        ActionEntity actionEntity = new ActionEntity(request);
        actionRepository.save(actionEntity);
        List<ActionParameter> actionParameterList = new ArrayList<>();

        for(int i = 0; i < request.getParams().size(); i++) {
            ActionParamsEntity actionParams = new ActionParamsEntity(actionEntity.getId(), request.getParams().get(i));
            actionParamsRepository.save(actionParams);
            actionParameterList.add(new ActionParameter(actionParams.getActionId(), actionParams.getName(), actionParams.getValue()));
        }

        ActionParameters actionParameters = new ActionParameters.Impl(actionParameterList);

        actionTimeoutCreateEventPublisher.send(new ActionTimeoutCreateEvent(actionEntity.getId(), timeout));
        log.info("Action with {} id created", actionEntity.getId());
        return new ActionDTO(actionEntity, actionParameters, timeout);

    }

    public void delete(final long id) {
        actionRepository.save(actionRepository.mustFindById(id).withDeleted(true));

        actionParamsRepository.saveAll(actionParamsRepository.findAllByActionId(id).stream().map(x -> x.withDeleted(true)).collect(Collectors.toList()));

        log.info("Action with {} id deleted", id);
    }

    public Action findById(final long id) {
        ActionEntity entity = actionRepository.mustFindByIdAndDeletedIsFalse(id);

        List<ActionParamsEntity> actionParamsEntities = actionParamsRepository.findAllByActionId(id);
        List<ActionParameter> actionParameterList = new ArrayList<>();

        for(int i = 0; i < actionParamsEntities.size(); i++)    {
            ActionParamsEntity source = actionParamsEntities.get(i);
            actionParameterList.add(new ActionParameter(source.getActionId(), source.getName(), source.getValue()));
        }

        ActionParameters actionParameters = new ActionParameters.Impl(actionParameterList);

        return new ActionDTO(entity, actionParameters, timeout);
    }
}
