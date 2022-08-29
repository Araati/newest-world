package com.newestworld.executor.service.impl;

import com.newestworld.commons.dto.ActionParams;
import com.newestworld.commons.model.Action;
import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.executor.dao.ActionParamsRepository;
import com.newestworld.executor.dao.ActionRepository;
import com.newestworld.executor.dto.ActionDTO;
import com.newestworld.executor.dto.ActionParamsDTO;
import com.newestworld.executor.model.entity.ActionEntity;
import com.newestworld.executor.service.ActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultActionService implements ActionService {

    private final ActionRepository actionRepository;
    private final ActionParamsRepository actionParamsRepository;

    @Override
    public List<Action> findAllByIds(final Collection<Long> ids) {
        final Iterable<ActionEntity> entityList = actionRepository.findAllById(ids);
        final List<Action> list = new ArrayList<>();
        for (var entity : entityList) {
            list.add(new ActionDTO(entity));
        }
        log.debug("Found {} actions", list.size());
        return list;
    }

    @Override
    public Optional<Action> findById(final long id) {
        return actionRepository.findById(id).map(ActionDTO::new);
    }

    @Override
    public ActionParameters findAllParamsByActionId(final long id) {
        var list = actionParamsRepository.findAllByActionId(id).stream()
                .map(x -> new ActionParameter(x.getActionId(), x.getName(), x.getValue()))
                .collect(Collectors.toList());

        return new ActionParameters.Impl(list);
    }
}
