package com.newestworld.executor.service;

import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.executor.dto.BasicActionDTO;
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

    public void execute(final ActionParameters input, final List<BasicAction> list) {
        if (!input.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                BasicAction basicAction = list.get(i);
                List<ActionParameter> actionParameters = basicAction.getParameters().getAll();
                for (int j = 0; j < actionParameters.size(); j++)   {
                    ActionParameter parameter = actionParameters.get(j);
                    if (input.getByName(parameter.getName()).isPresent())   {
                        ActionParameter updatedParameter = new ActionParameter(parameter.getActionId(), parameter.getName(),
                                input.mustGetByName(parameter.getName()));
                        actionParameters.set(j, updatedParameter);
                        list.set(i, new BasicActionDTO(basicAction.getId(), basicAction.getType(),
                                new ActionParameters.Impl(actionParameters), basicAction.getCreatedAt()));
                    }
                }
            }
        }
        list.forEach(this::execute);
    }
}
