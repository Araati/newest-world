package com.newestworld.content.service;

import com.newestworld.commons.dto.Action;
import com.newestworld.commons.dto.ActionParams;
import com.newestworld.content.dto.*;
import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.content.model.entity.ActionParamsEntity;
import com.newestworld.content.dao.ActionParamsRepository;
import com.newestworld.content.dao.ActionRepository;
import com.newestworld.content.dao.ActionTimeoutRepository;
import com.newestworld.content.model.entity.ActionEntity;
import com.newestworld.content.model.entity.ActionTimeoutEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionService {

    private final ActionRepository actionRepository;
    private final ActionParamsRepository actionParamsRepository;
    private final ActionTimeoutRepository actionTimeoutRepository;

    public Action create(ActionCreateDTO request) {
        ActionEntity actionEntity = new ActionEntity(request);
        actionRepository.save(actionEntity);
        List<ActionParams> actionParamsList = new ArrayList<>();
        for(int i = 0; i < request.getParams().size(); i++) {
            ActionParamsEntity actionParams = new ActionParamsEntity(actionEntity.getId(), request.getParams().get(i));
            actionParamsRepository.save(actionParams);
            actionParamsList.add(new ActionParamsDTO(actionParams));
        }
        ActionTimeoutEntity actionTimeoutEntity = new ActionTimeoutEntity(actionEntity.getId(), request);
        actionTimeoutRepository.save(actionTimeoutEntity);
        return new ActionDTO(actionEntity, actionParamsList, actionTimeoutEntity);

    }

    public void delete(long id) {
        ActionEntity entity = actionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Action", id));
        actionRepository.delete(entity);

        actionParamsRepository.deleteAll(actionParamsRepository.findAllByActionId(id));

        actionTimeoutRepository.delete(actionTimeoutRepository.findByActionId(id));
    }

    public Action findById(long id) {
        ActionEntity entity = actionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Action", id));
        List<ActionParams> actionParamsList = actionParamsRepository.findAllByActionId(id).stream().map(ActionParamsDTO::new).collect(Collectors.toList());
        ActionTimeoutEntity entity2 = actionTimeoutRepository.findByActionId(id);

        return new ActionDTO(entity, actionParamsList, entity2);
    }
}
