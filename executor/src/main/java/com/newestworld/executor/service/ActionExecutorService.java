package com.newestworld.executor.service;

import com.newestworld.commons.dto.ActionParams;
import com.newestworld.commons.event.ActionTimeoutBatchEvent;
import com.newestworld.commons.event.ActionTimeoutEvent;
import com.newestworld.commons.exception.ResourceNotFoundException;
import com.newestworld.commons.model.Action;
import com.newestworld.executor.dao.ActionParamsRepository;
import com.newestworld.executor.dao.ActionRepository;
import com.newestworld.executor.dto.ActionDTO;
import com.newestworld.executor.dto.ActionParamsDTO;
import com.newestworld.executor.model.entity.ActionEntity;
import com.newestworld.executor.model.entity.ActionParamsEntity;
import com.newestworld.executor.strategy.ActionExecutor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionExecutorService {

    private final ActionRepository actionRepository;
    private final ActionParamsRepository actionParamsRepository;

    private final List<ActionExecutor> executors;

    // TODO: 22.01.2023 Куча неиспользованных поисков параметров, последние три функции вообще не используются. Спросить у Нокса

    public void execute(final long id) {

        // TODO: 03.08.2022 Экзекутор НЕ ДОЛЖЕН общаться с БД
        ActionEntity actionEntity = actionRepository.mustFindById(id);
        List<ActionParams> actionParams = actionParamsRepository.findAllByActionId(id)
                .stream().map(ActionParamsDTO::new).collect(Collectors.toList());

        Action action = new ActionDTO(actionEntity);

        for (final ActionExecutor executor : executors) {
            if (executor.support(action)) {
                executor.exec(action);
            }
        }

        throw new RuntimeException(String.format("Action type - %d not supporting", action.getType()));
    }

    public void execute(final List<Long> listId) {
        final Iterable<ActionEntity> actionList = actionRepository.findAllById(listId);
        actionList.spliterator().forEachRemaining(this::execute);
    }

    private void execute(final ActionEntity action) {
        final List<ActionParamsEntity> actionParams = actionParamsRepository.findAllByActionId(action.getId());

    }

    @SneakyThrows
    public void execute(final ActionTimeoutBatchEvent x) {
        execute(x.getBatch().stream().map(ActionTimeoutEvent::getId).collect(Collectors.toList()));
    }
}
