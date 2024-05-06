package com.newestworld.executor.executors;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.Node;
import com.newestworld.executor.util.ExecutionContext;
import com.newestworld.streams.event.AbstractObjectUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ModifyExecutor implements ActionExecutor {

    @Override
    public String exec(final ExecutionContext context) {

        var target = context.getLocalVariable("target");
        var field = context.getLocalVariable("field");
        var value = context.getLocalVariable("value");

        Map<String, String> toUpdate = new HashMap<>();
        toUpdate.put(field.toString(), value.toString());
        context.addEvent(new AbstractObjectUpdateEvent(Long.parseLong(target.toString()), toUpdate));

        return context.getLocalVariable("next").toString();
    }

    @Override
    public boolean support(final Node node) {
        return node.getType() == ActionType.MODIFY;
    }
}
