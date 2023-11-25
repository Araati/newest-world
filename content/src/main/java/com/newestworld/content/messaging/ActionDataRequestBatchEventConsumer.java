package com.newestworld.content.messaging;

import com.newestworld.commons.model.CompoundAction;
import com.newestworld.content.service.CompoundActionService;
import com.newestworld.streams.event.ActionDataBatchEvent;
import com.newestworld.streams.event.ActionDataEvent;
import com.newestworld.streams.event.ActionDataRequestBatchEvent;
import com.newestworld.streams.event.ActionDataRequestEvent;
import com.newestworld.streams.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionDataRequestBatchEventConsumer implements Consumer<ActionDataRequestBatchEvent> {

    private final CompoundActionService compoundActionService;
    private final EventPublisher<ActionDataBatchEvent> actionDataBatchEventPublisher;

    @Override
    public void accept(final ActionDataRequestBatchEvent event)  {
        log.debug("data request received for {} actions", event.getSize());
        List<ActionDataRequestEvent> requests = new ArrayList<>(event.getBatch());
        List<ActionDataEvent> dataEvents = new ArrayList<>();

        for (ActionDataRequestEvent request : requests) {
            CompoundAction compoundAction = compoundActionService.findById(request.getId());
            dataEvents.add(new ActionDataEvent(compoundAction.getId(), compoundAction.getName(),
                    // TODO: 26.11.2023 Стоит ли передавать базовые действия? По идее стоит, но это идет в конфликт с интерфейсом...
                    // ...пользователь не должен видеть каждый шаг экшена, а экзекутор должен
                    compoundAction.getInput().getAll(), compoundAction.getCreatedAt()));
        }

        actionDataBatchEventPublisher.send(new ActionDataBatchEvent(dataEvents));
    }

}
