package com.newestworld.executor.service;

import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.executor.executors.ActionExecutor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionExecutorAggregator {

    private final List<ActionExecutor> executors;

    @SneakyThrows
    public void execute(final BasicAction current, final List<BasicAction> basicActions, Map<String, String> context) {
        var supported = executors.stream().filter(x -> x.support(current)).findFirst();
        if (supported.isEmpty()) {
            log.warn("BasicAction {} with type {} does not have executors", current.getId(), current.getType().name());
        } else {
            basicActions.remove(current);
            String next = supported.get().exec(current, basicActions, context);
            if (!next.isEmpty())    {
                var nextAction = basicActions.stream()
                        .filter(x -> next.equals(x.getLocalPosition().toString())).findFirst();
                //todo create more suitable exception for missing BasicAction
                execute(nextAction.orElseThrow(() -> new RuntimeException(String.format("BasicAction missing in CompoundAction %s", context.get("compound_id")))),
                        basicActions, context);
            }
        }

    }

    public void execute(final Long actionId, final ActionParameters input, final List<BasicAction> list) {
        Map<String, String> context = new HashMap<>();
        for (ActionParameter parameter : input.getAll()) {
            context.put(parameter.getName(), parameter.getValue().toString());
        }
        context.put("compound_id", actionId.toString());
        //todo create more suitable exception for missing BasicAction
        BasicAction start = list.stream().filter(x -> x.getType().equals(ActionType.START)).findFirst().orElseThrow(
                () -> new RuntimeException(String.format("StartAction is missing in CompoundAction %s", actionId)));
        list.remove(start);
        execute(start, list, context);
    }
}
