package com.newestworld.executor.executors;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.streams.event.AbstractObjectCreateEvent;
import com.newestworld.streams.event.AbstractObjectUpdateEvent;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAbstractObjectExecutor implements ActionExecutor {

    private final EventPublisher<AbstractObjectCreateEvent> abstractObjectCreateEventPublisher;

    @Override
    public String exec(final BasicAction current, final List<BasicAction> basicActions, Map<String, String> context) {
        var parameters = current.getParameters().getAll();
        var properties = new HashMap<String, String>();

        var name = current.getParameters().mustGetByName("name").getValue().toString();
        if (name.startsWith("$")) {
            name = context.get(name);
        }

        // Ignore first (name) and last (next) parameter
        for (int i = 1; i < parameters.size() - 1; i++)  {
            var parameter = parameters.get(i).getValue().toString();
            if (parameter.startsWith("$"))
                parameter = context.get(parameter);
            properties.put(parameters.get(i).getName(), parameter);
        }
        abstractObjectCreateEventPublisher.send(new AbstractObjectCreateEvent(name, properties));
        return current.getParameters().mustGetByName("next").getValue().toString();
    }

    @Override
    public boolean support(final BasicAction basicAction) {
        return basicAction.getType() == ActionType.CREATE_ABSTRACT_OBJECT;
    }
}
