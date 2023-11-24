package com.newestworld.executor.service;

import com.newestworld.commons.model.BasicAction;
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
    public void execute(final BasicAction basicAction) {
        var supported = executors.stream().filter(x -> x.support(basicAction)).collect(Collectors.toList());
        if (supported.isEmpty()) {
            log.warn("BasicAction {} with type {} does not have executors", basicAction.getId(), basicAction.getType().name());
        }

        supported.forEach(x -> x.exec(basicAction));
    }

    public void execute(final List<BasicAction> list) {
        list.forEach(this::execute);
    }
}
