package com.newestworld.executor.executors;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.BasicAction;
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
public class ModifyExecutor implements ActionExecutor {

    private final EventPublisher<AbstractObjectUpdateEvent> abstractObjectUpdateEventPublisher;

    @Override
    public String exec(final BasicAction current, final List<BasicAction> basicActions, Map<String, String> context) {
        var target = current.getParameters().mustGetByName("target").getValue().toString();
        if (target.startsWith("$")) {
            target = context.get(target);
        }

        var field = current.getParameters().mustGetByName("field").getValue().toString();
        if (field.startsWith("$")) {
            field = context.get(field);
        }

        var value = current.getParameters().mustGetByName("value").getValue().toString();
        if (value.startsWith("$")) {
            value = context.get(value);
        }

        Map<String, String> toUpdate = new HashMap<>();
        toUpdate.put(field, value);
        abstractObjectUpdateEventPublisher.send(new AbstractObjectUpdateEvent(Long.parseLong(target), toUpdate));

        return current.getParameters().mustGetByName("next").getValue().toString();
    }

    @Override
    public boolean support(final BasicAction basicAction) {
        return basicAction.getType() == ActionType.MODIFY;
    }
}
