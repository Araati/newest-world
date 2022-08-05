package com.newestworld.executor.strategy;

import com.newestworld.commons.dto.Action;
import com.newestworld.commons.dto.ActionParams;
import com.newestworld.executor.util.ActionType;
import com.newestworld.streams.EventPublisher;
import com.newestworld.streams.dto.ActionCreateEventDTO;
import com.newestworld.streams.dto.FactoryUpdateEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ActionFactoryStart implements ActionExecutor    {

    private final EventPublisher<FactoryUpdateEventDTO> publisher;
    private final EventPublisher<ActionCreateEventDTO> actionCreatePublisher;

    @Override
    public void exec(Action action) {

        // TODO: 01.08.2022 Не надо так
        Long target = null;

        for (ActionParams params : action.getParams()) {
            if (params.getName().equals("target")) {
                target = Long.valueOf(params.getValue());
            }
        }

        publisher.send(new FactoryUpdateEventDTO(target, Optional.empty(), Optional.empty()));
        HashMap<String, String> params = new HashMap<>();
        params.put("target", target.toString());
        // TODO: 05.08.2022 Значение с потолка
        params.put("amount", "1000");
        actionCreatePublisher.send(new ActionCreateEventDTO(1, params));
    }

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.START.getType();
    }
}
