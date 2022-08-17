package com.newestworld.executor.strategy;

import com.newestworld.commons.event.ActionDeleteEvent;
import com.newestworld.commons.event.FactoryUpdateEventDTO;
import com.newestworld.commons.model.Action;
import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.executor.service.ActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionAdd implements ActionExecutor {

    private final ActionService service;

    private final StreamBridge publisher;

    @Override
    public void exec(Action action) {

        final ActionParameters params = service.findAllParamsByActionId(action.getId());
        if (params.isEmpty()) {
            throw new IllegalArgumentException(String.format("Action parameters is not defined; action id %d", action.getId()));
        }

        var target = params.mustGetByName("target");
        var amount = params.mustGetByName("amount");

        publisher.send("producerFactoryUpdateEvent-out-0", new FactoryUpdateEventDTO((long) target.getValue(), null, (Long) amount.getValue()));
        publisher.send("producerActionDeletedEvent-out-0", new ActionDeleteEvent(action.getId()));

        // TODO: 05.08.2022 Этот экшен должен уметь пересоздаваться
        log.info("ActionAdd with {} id processed", action.getId());
    }

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.ADD;
    }
}
