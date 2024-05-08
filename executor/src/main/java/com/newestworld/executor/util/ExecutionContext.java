package com.newestworld.executor.util;

import com.newestworld.commons.model.ModelParameter;
import com.newestworld.commons.model.ModelParameters;
import com.newestworld.streams.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ExecutionContext {

    private ModelParameters actionScope = new ModelParameters.Impl();
    private final Map<String, String> nodeScope = new HashMap<>();
    private final List<Event> events = new ArrayList<>();

    public void addEvent(final Event event) {
        events.add(event);
    }

    public void createActionScope(final ModelParameters parameters)    {
        actionScope = parameters;
    }

    public void updateActionScope(final ModelParameter parameter)    {
        actionScope.add(parameter);
    }

    public ModelParameter getActionVariable(final String name)    {
        return actionScope.mustGetByName(name);
    }

    public void createNodeScope(final Map<String, String> scope)    {
        nodeScope.clear();
        nodeScope.putAll(scope);
        for (Map.Entry<String, String> pair : nodeScope.entrySet()) {
            if (pair.getValue().startsWith("$")) {
                nodeScope.put(pair.getKey(), actionScope.mustGetByName(pair.getValue().substring(1)).getData());
            }
        }
    }

    public void updateNodeScope(final Map<String, String> scope)    {
        nodeScope.putAll(scope);
    }

    public Object getNodeVariable(final String name)    {
        return nodeScope.get(name);
    }
}
