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

        var target = context.getNodeVariable("target");
        var field = context.getNodeVariable("field");
        var value = context.getNodeVariable("value");

        Map<String, String> toUpdate = new HashMap<>();
        toUpdate.put(field.toString(), value.toString());
        context.addEvent(new AbstractObjectUpdateEvent(Long.parseLong(target.toString()), toUpdate));

        return context.getNodeVariable("next").toString();
    }

    @Override
    public boolean support(final Node node) {
        return node.getType() == ActionType.MODIFY;
    }
}
