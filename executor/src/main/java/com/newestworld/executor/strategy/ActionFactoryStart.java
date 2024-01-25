package com.newestworld.executor.strategy;

import com.newestworld.commons.model.ActionParameters;
import com.newestworld.commons.model.ActionType;
import com.newestworld.commons.model.BasicAction;
import com.newestworld.streams.event.CompoundActionCreateEvent;
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
public class ActionFactoryStart implements ActionExecutor    {

    private final EventPublisher<FactoryUpdateEvent> factoryUpdateEventPublisher;
    private final EventPublisher<ActionDeleteEvent> actionDeleteEventPublisher;
    private final EventPublisher<CompoundActionCreateEvent> actionCreateEventPublisher;

    @Override
    public void exec(final BasicAction basicAction) {

       final ActionParameters parameters = basicAction.getParameters();
        if(parameters.isEmpty())    {
            throw new IllegalArgumentException(String.format("BasicAction parameters is not defined; basicAction id %d", basicAction.getId()));
        }

        var target = parameters.mustGetByName("target");

        factoryUpdateEventPublisher.send(
                new FactoryUpdateEvent(Long.parseLong(target.getValue().toString()),
                        true,
                        null));

        actionDeleteEventPublisher.send(new ActionDeleteEvent(basicAction.getId()));
        HashMap<String, String> createParams = new HashMap<>();
        createParams.put("target", target.getValue().toString());
        // TODO: 11.01.2023 test values
        createParams.put("amount", "1000");
        createParams.put("repeat", "-1");

        // FIXME: 28.11.2023 
        //actionCreateEventPublisher.send(new CompoundActionCreateEvent(ActionType.ADD.getId(), createParams));

        log.info("ActionFactoryStart with {} id processed", basicAction.getId());
    }

    @Override
    public boolean support(final BasicAction basicAction) {
        return basicAction.getType() == ActionType.START;
    }
}
