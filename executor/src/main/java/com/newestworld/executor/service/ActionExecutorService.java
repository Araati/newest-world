package com.newestworld.executor.service;

import com.newestworld.commons.dto.Action;
import com.newestworld.commons.dto.ActionParams;
import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.executor.dao.ActionParamsRepository;
import com.newestworld.executor.dao.ActionRepository;
import com.newestworld.executor.dto.ActionDTO;
import com.newestworld.executor.dto.ActionParamsDTO;
import com.newestworld.executor.model.entity.ActionEntity;
import com.newestworld.executor.model.entity.ActionParamsEntity;
import com.newestworld.executor.strategy.ActionExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionExecutorService {

    private final ActionRepository actionRepository;
    private final ActionParamsRepository actionParamsRepository;

    private final List<ActionExecutor> executors;

    public void execute(final long id) {

        ActionEntity actionEntity = actionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Action", id));
        List<ActionParams> actionParams = actionParamsRepository.findAllByActionId(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActionParams", id))
                .stream().map(ActionParamsDTO::new).collect(Collectors.toList());

        Action action = new ActionDTO(actionEntity, actionParams);

        for (final ActionExecutor executor : executors) {
            if (executor.support(action)) {
                executor.exec(action);
            }
        }

        throw new RuntimeException(String.format("Action type - %d not supporting", action.getType()));
    }
}
