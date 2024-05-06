package com.newestworld.executor.executors;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.Node;
import com.newestworld.executor.util.ExecutionContext;
import com.newestworld.streams.event.AbstractObjectCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAbstractObjectExecutor implements ActionExecutor {

    @Override
    public String exec(final ExecutionContext context) {
        var properties = new HashMap<String, String>();

        var name = context.getLocalVariable("name");

        // Ignore first (name) and last (next) parameter
        for (var pair : context.getLocal().entrySet())  {
            if (!(pair.getKey().equals("name") || pair.getKey().equals("next")))   {
                properties.put(pair.getKey(), pair.getValue());
            }
        }
        context.addEvent(new AbstractObjectCreateEvent(name.toString(), properties));
        return context.getLocalVariable("next").toString();
    }

    @Override
    public boolean support(final Node node) {
        return node.getType() == ActionType.CREATE_ABSTRACT_OBJECT;
    }
}
