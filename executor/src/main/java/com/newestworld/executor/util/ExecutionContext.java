package com.newestworld.executor.util;

import com.newestworld.commons.model.ActionParameter;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.streams.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
@RequiredArgsConstructor
public class ExecutionContext {

    private final Map<String, String> global = new HashMap<>();
    private final Map<String, String> local = new HashMap<>();
    private final List<Event> events = new ArrayList<>();

    public void addEvent(final Event event) {
        events.add(event);
    }

    public void addGlobalParameters(final ActionParameters parameters)    {
        if (parameters.getAll() != null) {
            for (ActionParameter parameter : parameters.getAll()) {
                global.put(parameter.getName(), parameter.getValue().toString());
            }
        }
    }

    public void createLocalScope(final ActionParameters parameters)    {
        local.clear();
        local.putAll(global);
        if (parameters.getAll() != null) {
            for (ActionParameter parameter : parameters.getAll()) {
                if (parameter.getValue().toString().startsWith("$"))    {
                    local.put(parameter.getName(), global.get(parameter.getValue().toString()));
                } else {
                    local.put(parameter.getName(), parameter.getValue().toString());
                }
            }
        }
    }

    public void updateGlobalVariable(final String name, final String value)    {
        global.put(name, value);
    }

    public Object getLocalVariable(final String name)    {
        return local.get(name);
    }
}
