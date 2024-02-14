package com.newestworld.executor.executors;

import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.streams.event.ActionDeleteEvent;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartExecutor implements ActionExecutor {

    private final EventPublisher<ActionDeleteEvent> actionDeleteEventEventPublisher;

    @Override
    public String exec(final BasicAction current, final List<BasicAction> basicActions, Map<String, String> context) {
        actionDeleteEventEventPublisher.send(new ActionDeleteEvent(Long.parseLong(context.get("compound_id"))));
        return current.getParameters().mustGetByName("next").getValue().toString();
    }

    @Override
    public boolean support(final BasicAction basicAction) {
        return basicAction.getType() == ActionType.START;
    }
}
