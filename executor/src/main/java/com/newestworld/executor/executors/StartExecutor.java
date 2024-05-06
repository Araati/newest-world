package com.newestworld.executor.executors;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.Node;
import com.newestworld.executor.util.ExecutionContext;
import com.newestworld.streams.event.ActionDeleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartExecutor implements ActionExecutor {

    @Override
    public String exec(final ExecutionContext context) {
        context.addEvent(new ActionDeleteEvent(Long.parseLong(context.getLocalVariable("action_id").toString())));
        return context.getLocalVariable("next").toString();
    }

    @Override
    public boolean support(final Node node) {
        return node.getType() == ActionType.START;
    }
}
