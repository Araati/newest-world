package com.newestworld.executor.service;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.ModelParameter;
import com.newestworld.commons.model.Node;
import com.newestworld.executor.dto.NodeDTO;
import com.newestworld.executor.executors.ActionExecutor;
import com.newestworld.executor.util.ExecutionContext;
import com.newestworld.streams.event.ActionDataEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionExecutorAggregator {

    private final List<ActionExecutor> executors;
    private ExecutionContext context;

    public void startExecution(final ActionDataEvent event) {
        List<Node> steps = event.getNodes()
                .stream().map(NodeDTO::new).collect(Collectors.toList());

        context = new ExecutionContext();
        context.createActionScope(event.getInput());
        context.updateActionScope(new ModelParameter("action_id", true, event.getActionId().toString(), "int", null, null));

        //todo create more suitable exception for missing Node
        Node start = steps.stream().filter(x -> x.getType().equals(ActionType.START)).findFirst().orElseThrow(
                () -> new RuntimeException(String.format("StartAction is missing in Action %s", event.getActionId())));
        steps.remove(start);
        execute(start, steps);
    }

    @SneakyThrows
    private void execute(final Node current, final List<Node> nodes) {
        var supported = executors.stream().filter(x -> x.support(current)).findFirst();
        if (supported.isEmpty()) {
            log.warn("Type {} node does not have executors", current.getType().name());
        } else {
            nodes.remove(current);

            context.createNodeScope(current.getParameters());
            ModelParameter actionId = context.getActionVariable("action_id");
            context.updateNodeScope(Map.of(actionId.getName(), actionId.getData()));
            String next = supported.get().exec(context);

            if (!next.isEmpty())    {
                var nextAction = nodes.stream()
                        .filter(x -> next.equals(x.getPosition().toString())).findFirst();
                //todo create more suitable exception for missing Node
                execute(nextAction.orElseThrow(() -> new RuntimeException(String.format("Node missing in Action %s",
                                Long.parseLong(context.getNodeVariable("action_id").toString())))),
                        nodes);
            }
        }
    }
}
