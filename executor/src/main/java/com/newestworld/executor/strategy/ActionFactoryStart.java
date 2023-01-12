package com.newestworld.executor.strategy;

import com.newestworld.commons.event.ActionCreateEvent;
import com.newestworld.commons.event.ActionDeleteEvent;
import com.newestworld.commons.event.FactoryUpdateEvent;
import com.newestworld.commons.model.Action;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.executor.service.ActionService;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionFactoryStart implements ActionExecutor    {

    private final ActionService actionService;

    private final EventPublisher<FactoryUpdateEvent> factoryUpdateEventPublisher;
    private final EventPublisher<ActionDeleteEvent> actionDeleteEventPublisher;
    private final EventPublisher<ActionCreateEvent> actionCreateEventPublisher;

    @Override
    public void exec(Action action) {

       final ActionParameters params = actionService.findAllParamsByActionId(action.getId());
        if(params.isEmpty())    {
            throw new IllegalArgumentException(String.format("Action parameters is not defined; action id %d", action.getId()));
        }

        var target = params.mustGetByName("target");

        factoryUpdateEventPublisher.send(
                new FactoryUpdateEvent(Long.parseLong(target.getValue().toString()),
                        true,
                        null));

        actionDeleteEventPublisher.send(new ActionDeleteEvent(action.getId()));
        HashMap<String, String> createParams = new HashMap<>();
        createParams.put("target", target.getValue().toString());
        // TODO: 11.01.2023 test values
        createParams.put("amount", "1000");
        createParams.put("repeat", "-1");

        actionCreateEventPublisher.send(new ActionCreateEvent(ActionType.ADD.getId(), createParams));

        log.info("ActionFactoryStart with {} id processed", action.getId());
    }

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.START;
    }
}
