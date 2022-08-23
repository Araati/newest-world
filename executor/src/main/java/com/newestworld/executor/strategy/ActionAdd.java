package com.newestworld.executor.strategy;

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

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionAdd implements ActionExecutor {

    private final ActionService service;

    private final EventPublisher<FactoryUpdateEvent> factoryUpdateEventPublisher;
    private final EventPublisher<ActionDeleteEvent> actionDeleteEventEventPublisher;

    @Override
    public void exec(Action action) {

        final ActionParameters params = service.findAllParamsByActionId(action.getId());
        if (params.isEmpty()) {
            throw new IllegalArgumentException(String.format("Action parameters is not defined; action id %d", action.getId()));
        }

        var target = params.mustGetByName("target");
        var amount = params.mustGetByName("amount");

        factoryUpdateEventPublisher.send(
                new FactoryUpdateEvent(Long.parseLong(target.getValue().toString()),
                        null,
                        Long.parseLong(amount.getValue().toString()))
        );

        actionDeleteEventEventPublisher.send(new ActionDeleteEvent(action.getId()));

        // TODO: 05.08.2022 Этот экшен должен уметь пересоздаваться
        log.info("ActionAdd with {} id processed", action.getId());
    }

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.ADD;
    }
}
