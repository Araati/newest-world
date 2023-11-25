package com.newestworld.content.service;

import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.content.dao.ActionParamsRepository;
import com.newestworld.content.dto.ActionParamsCreateDTO;
import com.newestworld.content.model.entity.ActionParamsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionParamsService {

    private final ActionParamsRepository actionParamsRepository;

    public ActionParameters create(final long actionId, final List<ActionParamsCreateDTO> request) {
        List<ActionParameter> parameters = new ArrayList<>();
        for (final ActionParamsCreateDTO actionParamsCreateDTO : request) {
            actionParamsRepository.save(new ActionParamsEntity(actionId, actionParamsCreateDTO));
            parameters.add(new ActionParameter(actionId, actionParamsCreateDTO.getName(), actionParamsCreateDTO.getValue()));
        }
        return new ActionParameters.Impl(parameters);
    }

    public void delete(final long actionId) {
        actionParamsRepository.saveAll(actionParamsRepository.findAllByActionIdAndDeletedIsFalse(actionId).stream()
                .map(x -> x.withDeleted(true)).collect(Collectors.toList()));
    }

    public ActionParameters findById(final long actionId)   {
        List<ActionParamsEntity> actionParamsEntities = actionParamsRepository.findAllByActionIdAndDeletedIsFalse(actionId);
        List<ActionParameter> actionParameterList = new ArrayList<>();
        for (ActionParamsEntity source : actionParamsEntities) {
            actionParameterList.add(new ActionParameter(source.getActionId(), source.getName(), source.getValue()));
        }
        return new ActionParameters.Impl(actionParameterList);
    }
}
