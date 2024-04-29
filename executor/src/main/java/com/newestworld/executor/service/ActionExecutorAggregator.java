package com.newestworld.executor.service;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.executor.dto.BasicActionDTO;
import com.newestworld.executor.executors.ActionExecutor;
import com.newestworld.executor.util.ExecutionContext;
import com.newestworld.streams.event.CompoundActionDataEvent;
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
    private final ExecutionContext context;

    public void startExecution(final CompoundActionDataEvent event) {
        List<BasicAction> steps = event.getBasicActions()
                .stream().map(BasicActionDTO::new).collect(Collectors.toList());
        context.addGlobalParameters(event.getInput());
        context.updateGlobalVariable("compound_id", event.getActionId().toString());

        //todo create more suitable exception for missing BasicAction
        BasicAction start = steps.stream().filter(x -> x.getType().equals(ActionType.START)).findFirst().orElseThrow(
                () -> new RuntimeException(String.format("StartAction is missing in CompoundAction %s", event.getActionId())));
        steps.remove(start);
        execute(start, steps);
    }

    @SneakyThrows
    private void execute(final BasicAction current, final List<BasicAction> basicActions) {
        var supported = executors.stream().filter(x -> x.support(current)).findFirst();
        if (supported.isEmpty()) {
            log.warn("BasicAction {} with type {} does not have executors", current.getId(), current.getType().name());
        } else {
            basicActions.remove(current);

            context.createLocalScope(current.getParameters());
            String next = supported.get().exec(context);

            if (!next.isEmpty())    {
                var nextAction = basicActions.stream()
                        .filter(x -> next.equals(x.getLocalPosition().toString())).findFirst();
                //todo create more suitable exception for missing BasicAction
                execute(nextAction.orElseThrow(() -> new RuntimeException(String.format("BasicAction missing in CompoundAction %s",
                                Long.parseLong(context.getLocalVariable("compound_id").toString())))),
                        basicActions);
            }
        }
    }
}
