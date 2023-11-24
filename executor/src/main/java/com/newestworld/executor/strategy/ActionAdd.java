package com.newestworld.executor.strategy;

import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.streams.event.ActionCreateEvent;
import com.newestworld.streams.event.ActionDeleteEvent;
import com.newestworld.streams.event.FactoryUpdateEvent;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionAdd implements ActionExecutor {

    private final EventPublisher<FactoryUpdateEvent> factoryUpdateEventPublisher;
    private final EventPublisher<ActionDeleteEvent> actionDeleteEventEventPublisher;
    private final EventPublisher<ActionCreateEvent> actionCreateEventPublisher;

    @Override
    public void exec(final BasicAction basicAction) {

        final ActionParameters parameters = basicAction.getParameters();
        if (parameters.isEmpty()) {
            throw new IllegalArgumentException(String.format("BasicAction parameters is not defined; basicAction id %d", basicAction.getId()));
        }

        var target = parameters.mustGetByName("target");
        var amount = parameters.mustGetByName("amount");
        var repeat = parameters.mustGetByName("repeat");

        factoryUpdateEventPublisher.send(
                new FactoryUpdateEvent(Long.parseLong(target.getValue().toString()),
                        null,
                        Long.parseLong(amount.getValue().toString()))
        );

        actionDeleteEventEventPublisher.send(new ActionDeleteEvent(basicAction.getId()));

        if(Long.parseLong(repeat.getValue().toString()) == -1 || Long.parseLong(repeat.getValue().toString()) > 0)  {
            HashMap<String, String> createParams = new HashMap<>();
            createParams.put("target", target.getValue().toString());
            createParams.put("amount", amount.getValue().toString());
            if(Long.parseLong(repeat.getValue().toString()) == -1)
                createParams.put("repeat", "-1");
            else
                createParams.put("repeat", String.valueOf(Long.parseLong(repeat.getValue().toString())-1));

            actionCreateEventPublisher.send(new ActionCreateEvent(ActionType.ADD.getId(), createParams));
        }

        log.info("ActionAdd with {} id processed", basicAction.getId());
    }

    @Override
    public boolean support(final BasicAction basicAction) {
        return basicAction.getType() == ActionType.ADD;
    }
}
