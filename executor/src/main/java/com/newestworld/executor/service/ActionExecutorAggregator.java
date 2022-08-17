package com.newestworld.executor.service;

import com.newestworld.commons.model.Action;
import com.newestworld.executor.strategy.ActionExecutor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionExecutorAggregator {

    private final List<ActionExecutor> executors;

    @SneakyThrows
    public void execute(final Action action) {
        var supported = executors.stream().filter(x -> x.support(action)).collect(Collectors.toList());
        if (supported.isEmpty()) {
            log.warn("Action {} with type {} does not have executors", action.getId(), action.getType().name());
        }

        supported.forEach(x -> x.exec(action));
    }

    public void execute(final List<Action> list) {
        list.forEach(this::execute);
    }
}
