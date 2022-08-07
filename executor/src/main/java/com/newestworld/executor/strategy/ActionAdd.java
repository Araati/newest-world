package com.newestworld.executor.strategy;

import com.newestworld.commons.dto.Action;
import com.newestworld.commons.dto.ActionParams;
import com.newestworld.executor.util.ActionType;
import com.newestworld.streams.EventPublisher;
import com.newestworld.streams.dto.ActionDeleteEventDTO;
import com.newestworld.streams.dto.FactoryUpdateEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionAdd implements ActionExecutor    {

    private final EventPublisher<FactoryUpdateEventDTO> publisher;
    private final EventPublisher<ActionDeleteEventDTO> deletePublisher;

    @Override
    public void exec(Action action) {

        // TODO: 01.08.2022 Не надо так
        Long target = null;
        Long amount = null;

        for(ActionParams params : action.getParams())   {
            if(params.getName().equals("target"))    {;
                target = Long.valueOf(params.getValue());
            } else if(params.getName().equals("amount")) {
                amount = Long.valueOf(params.getValue());
            }
        }
        // TODO: 01.08.2022 Точно Optional.ofNullable()?
        publisher.send(new FactoryUpdateEventDTO(target, Optional.ofNullable(null), Optional.ofNullable(amount)));
        deletePublisher.send(new ActionDeleteEventDTO(action.getId()));
        // TODO: 05.08.2022 Этот экшен должен уметь пересоздаваться
        log.info("ActionAdd with {} id processed", action.getId());
    }

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.ADD.getType();
    }
}
